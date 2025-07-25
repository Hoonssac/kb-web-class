package org.scoula.board.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.mapper.BoardMapper;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;
import org.scoula.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final static String BASE_DIR = "/Users/hoonssac/Desktop";

	final private BoardMapper mapper;

	@Override
	public Page<BoardDTO> getPage(PageRequest pageRequest) {
		List<BoardVO> boards = mapper.getPage(pageRequest);
		int totalCount = mapper.getTotalCount();

		return Page.of(pageRequest, totalCount, boards.stream().map(BoardDTO::of).toList());
	}

	@Override
	public List<BoardDTO> getList() {
		log.info("getList.........");

		// VO -> DTO
		return mapper.getList().stream()
			.map(BoardDTO::of)
			.toList();
	}

	@Override
	public BoardDTO get(Long no) {
		log.info("get........" + no);
		BoardDTO board = BoardDTO.of(mapper.get(no));

		log.info("====================" + board);
		return Optional.ofNullable(board)
			.orElseThrow(NoSuchElementException::new);
	}

	// 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
	// RuntimeException인 경우만 자동 rollback.
	@Transactional
	@Override
	public BoardDTO create(BoardDTO board) {
		log.info("create....." + board);

		BoardVO boardVO = board.toVo();
		mapper.create(boardVO);

		// 파일 업로드 처리
		List<MultipartFile> files = board.getFiles();
		if (files != null && !files.isEmpty()) {
			upload(boardVO.getNo(), files);
		}
		return get(boardVO.getNo());
	}

	@Transactional
	@Override
	public BoardDTO update(BoardDTO board) {
		log.info("update......" + board);
		BoardVO boardVO = board.toVo();
		log.info("update......" + boardVO);

		mapper.update(board.toVo());

		// 파일 업로드 처리
		List<MultipartFile> files = board.getFiles();
		if (files != null && !files.isEmpty()) {
			upload(board.getNo(), files);
		}
		return get(board.getNo());
	}

	@Transactional
	@Override
	public BoardDTO delete(Long no) {
		log.info("delete......" + no);
		BoardDTO board = get(no);

		// 게시글 삭제
		mapper.delete(no);
		return board;
	}

	// 파일 업로드 처리
	private void upload(Long bno, List<MultipartFile> files) {
		for (MultipartFile part : files) {
			if (part.isEmpty()) {
				continue;
			}
			try {
				// 실제 파일 업로드
				String uploadPath = UploadFiles.upload(BASE_DIR, part);

				// DB 등록용 VO 생성
				BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
				mapper.createAttachment(attach);

			} catch (IOException e) {
				throw new RuntimeException(e); // @Transactional 에서 감지, 자동 rollback
			}
		}
	}

	// 첨부파일 한 개 얻기
	@Override
	public BoardAttachmentVO getAttachment(Long no) {
		return mapper.getAttachment(no);
	}

	@Override
	public boolean deleteAttachment(Long no) {
		return mapper.deleteAttachment(no) == 1;
	}
}
