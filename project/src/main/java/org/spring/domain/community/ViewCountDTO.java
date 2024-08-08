package org.spring.domain.community;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ViewCountDTO {

	private Integer view_bno;
	private Integer view_user_num;
	private Integer community_bno;
	private Timestamp view_date;
	
}
