package org.spring.domain.job;

import lombok.Data;

@Data
public class JobPageDTO {

	private int startPage; // 시작 페이지 1
	private int endPage; // 끝 페이지

	private int total; // 전체 테이블의 데이터 갯수, db에서 확인

	private boolean prev, next; // 이전, 다음 페이지가 있는지 여부 체크..

	private JobCriteria cri;

	// 총 페이지 계산: total, cri
	public JobPageDTO(JobCriteria cri, int total) {
		this.total = total;
		this.cri = cri; // amount=10

		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10; // 1,2,3,4,5 라고 치면 어떤 페이지로 가도 현재 마지막 페이지는 5
		this.startPage = endPage - 9;

		int realEndPage = (int) Math.ceil((total * 1.0 / cri.getAmount())); // 전체 페이지 수
		
		// 마지막 페이지가 43인데 50 페이지까지 나오면 버려지는 페이지들이 나오니 수정!
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
}
