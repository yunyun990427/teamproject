package org.spring.domain;

import lombok.Data;

@Data
public class RegisterDTO {
	private String user_email;
	private String user_pw;
	private String user_name;
	private String user_phone;
}
