package org.scoula.member.dto;

import java.util.Date;
import java.util.List;

import org.scoula.security.account.domain.MemberVO;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	private String username;
	private String email;
	private Date regDate;
	private Date updateDate;
	private MultipartFile avatar;
	private List<String> authList; // 권한 목록, join 처리 필요

	// VO -> DTO
	public static MemberDTO of(MemberVO m) {
		return MemberDTO.builder()
			.username(m.getUsername())
			.email(m.getEmail())
			.regDate(m.getRegDate())
			.updateDate(m.getUpdateDate())
			.authList(m.getAuthList().stream().map(a -> a.getAuth()).toList())
			.build();
	}

	// DTO -> VO
	public MemberVO toVO() {
		return MemberVO.builder()
			.username(username)
			.email(email)
			.regDate(regDate)
			.updateDate(updateDate)
			.build();
	}
}
