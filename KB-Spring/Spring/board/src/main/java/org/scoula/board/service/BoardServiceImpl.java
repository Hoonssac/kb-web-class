package org.scoula.board.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.scoula.board.domain.BoardVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	final private BoardMapper mapper;

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
		return Optional.ofNullable(board)
			.orElseThrow(NoSuchElementException::new);
	}

	@Override
	public void create(BoardDTO board) {
		log.info("create....." + board);

		BoardVO vo = board.toVo();
		mapper.create(vo);
		board.setNo(vo.getNo());
	}

	@Override
	public boolean update(BoardDTO board) {
		log.info("update......" + board);
		return mapper.update(board.toVo()) == 1;
	}

	@Override
	public boolean delete(Long no) {
		log.info("delete......" + no);
		return mapper.delete(no) == 1;
	}
}
