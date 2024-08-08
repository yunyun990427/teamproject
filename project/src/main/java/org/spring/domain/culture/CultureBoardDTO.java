package org.spring.domain.culture;

import lombok.Data;

@Data
	public class CultureBoardDTO {
	
	private int culture_bno;
	private String culture_classify;
	private String culture_title;
	private String culture_place;
	private String culture_regdate;
	private int culture_viewcnt;
	private String culture_area;
	private String culture_sitelink;
	private String culture_img;
	private String culture_target;
	private String culture_booleanfee;
	private double culture_lati_x;
	private double culture_longi_y;
	
	public CultureBoardDTO() {}
	
	public CultureBoardDTO(int culture_bno, String culture_classify, String culture_title, String culture_place, String culture_regdate, int culture_viewcnt) {
		super();
		this.culture_bno = culture_bno;
		this.culture_classify = culture_classify;
		this.culture_title = culture_title;
		this.culture_place = culture_place;
		this.culture_regdate = culture_regdate;
		this.culture_viewcnt = culture_viewcnt;
	}
	
	public CultureBoardDTO(int culture_bno, String culture_classify, String culture_title, String culture_place,
	String culture_regdate, int culture_viewcnt, String culture_area, String culture_sitelink,
	String culture_img, String culture_target, String culture_booleanfee, double culture_lati_x, double culture_longi_y) {
		super();
		this.culture_bno = culture_bno;
		this.culture_classify = culture_classify;
		this.culture_title = culture_title;
		this.culture_place = culture_place;
		this.culture_regdate = culture_regdate;
		this.culture_viewcnt = culture_viewcnt;
		this.culture_area = culture_area;
		this.culture_sitelink = culture_sitelink;
		this.culture_img = culture_img;
		this.culture_target = culture_target;
		this.culture_booleanfee = culture_booleanfee;
		
		this.culture_lati_x = culture_lati_x;
		this.culture_longi_y = culture_longi_y;
	}
	
	
	
	}