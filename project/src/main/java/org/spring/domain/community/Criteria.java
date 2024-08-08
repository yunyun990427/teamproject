package org.spring.domain.community;

import lombok.Data;

// 사용자의 선택에 따라 결과가 달라질 경우 필요한 필드,기능 구현
@Data
public class Criteria {

	// 현재 사용자의 페이지 위치 : 초기값 1
	private int pageNum;
	// 한 페이지에 표시될 게시물 갯수 : 10
	private int amount;
	// 페이지에 따라 첫번째 글이 어디부터 시작해야 하는지 저장.
	private int start;
	// 검색에 필요한 필드 선언
	private String type; // 종류
	private String keyword; // 검색어
	private String area;

	// 기본생성자 : 첫 페이지(1), 페이지당 10개 게시물
	public Criteria() {
		this(1, 10); // 기본값: 1페이지, 10개 게시물
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.start = (pageNum - 1) * amount; // 시작 인덱스 계산
	}



	public void type(String searchType) {
		this.type = searchType;
	}

	public void keyword(String keyword) {
		this.keyword = keyword;
	}

	public void area(String area) {
		this.area = area;
	}
	
	public int getStart() {
		return (this.pageNum - 1) * this.amount;
	}

	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}


}
