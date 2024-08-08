package org.spring.domain;

import lombok.Data;

@Data
public class BookmarkDTO {
	private String joRegistNo;
	private String bsnsSumryCn;
    private String cmpnyNm;
    private String receptClosNm;
    private String hopeWage;
    
	private int user_num;
	private String serviceID;
	private String serviceName;
    private String serviceContent;
    private String serviceDeadline; 
    
	private String culture_bno;
	private String culture_classify;
	private String culture_title;
	private String culture_place;
}
