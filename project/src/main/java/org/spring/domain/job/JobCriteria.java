package org.spring.domain.job;

import lombok.Data;

@Data
public class JobCriteria {
	private int pageNum; // 현재 페이징 번호
	
	private int amount; // 한 페이지에 표시될 게시물의 갯수 : 
	
	private int start; // 페이지에 따라 첫번째 글이 어디부터 시작해야 하는지 저장.
	
	// 검색에 필요한 필드 선언
	private String type; // 종류
	private String keyword; // 검색어
	private String district;  // 자치구 필드 추가
	private String wageType; // 급여 타입
	private String career; // 경력
	private String education; // 학력
	private String workDay; // 근무일 수
	
	// 기본 생성자 : 첫 페이지(1), 페이지 당 10개 게시물
	public JobCriteria() {
		this(1,10);
	}
	public JobCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.start = calculateStart();
	}
	public JobCriteria(int pageNum, int amount, String type, String keyword) {
		this.pageNum=pageNum;
		this.amount=amount;
		this.type=type;
		this.keyword=keyword;
	}
	
	public int getStart() {
		return calculateStart();
	}
	
	// 페이지 시작 위치 계산 메서드
	private int calculateStart() {		
		// 1페이지 -> 0 , 2페이지 -> 10
		return (this.pageNum-1) * this.amount;
	}
}
