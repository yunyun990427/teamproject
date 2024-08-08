package org.spring.domain.community;

import lombok.Data;

@Data
public class PageDTO {
	
	// 페이징 처리에서 표시될 시작 페이지 1
	private int startPage;
	
	// 페이징 처리에 표시될 끝 페이지
	private int endPage;
	
	// 전체 테이블의 데이터 갯수, DB에서 확인
	private int total;
	
	// 이전, 다음 페이지가 있는지 여부 판단
	private boolean prev,next;
	
	private Criteria cri;


    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        int realEndPage = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

        if (realEndPage < this.endPage) {
            this.endPage = realEndPage;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEndPage;
    }

	
}
