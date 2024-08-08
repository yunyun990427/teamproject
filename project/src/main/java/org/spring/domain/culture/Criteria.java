package org.spring.domain.culture;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private int start;
	
	private String area;
	private String classify;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.start = getOffset();
	}
	
	public int getOffset() {
		return (this.pageNum-1) * this.amount;
	}
}
