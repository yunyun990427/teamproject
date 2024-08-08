package org.spring.domain.policy;

import lombok.Data;

@Data
public class PageDTO {

	// 페이징 처리에서 표시될 시작 페이지 1
	private int startPage;
	
	// 페이징 처리에서 표시될 끝 페이지
	private int endPage;
	
	// 전체 테이블의 데이터 개수, DB에서 확인
	private int total;
	
	// 이전, 다음 페이지가 있는지 여부 판단
	private boolean prev, next;
	
	private Criteria cri;
	
	// 총 페이지 계산: total, cri
	public PageDTO(Criteria cri, int total) {
		this.cri = cri; // amount : 10
		this.total = total; // 40
		
		// 현재 페이지가 속한 페이지 그룹의 마지막 페이지 번호
		this.endPage = (int) (Math.ceil (cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		
		// 전체 페이지 수
		// Math.ceil(): 소수점이 존재하면 무조건 올림
		// 1.0 곱하는 이유: int와 int를 나누면 int가 나오기 때문에
		int realEndPage = (int) Math.ceil((total*1.0) / cri.getAmount());
		
		// 마지막 페이지 번호가 실제 페이지 수보다 큰 경우 조정
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		
		// 이전, 다음 페이지가 있는지 여부 판단
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
}