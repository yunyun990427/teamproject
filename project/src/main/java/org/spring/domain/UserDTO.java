package org.spring.domain;


import lombok.Data;

@Data
public class UserDTO {
	private Integer user_num;
    private String user_email;
    private String social_user_email;
    private String user_pw;
    private String user_name;
    private String user_phone;
    private String user_interest;
    private String login_type;
    
   
    
    private String pw;
    private String email;
    
    
    public UserDTO() {}
    
    public UserDTO(String user_email) {
    	this.user_email = user_email;
    }
    
    
    public UserDTO(Integer user_num, String user_email, String user_pw, String user_name, String user_phone, String user_interest) {
        this.user_num = user_num;
    	this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_interest = user_interest;
    }

    public UserDTO(Integer user_num,String user_email, String user_name) {
        this.user_num = user_num;
    	this.user_email = user_email;
        this.user_name = user_name;
    }


	public UserDTO(Integer user_num, String social_user_email, String user_name, String user_phone) {
		super();
		this.user_num = user_num;
		this.social_user_email = social_user_email;
		this.user_name = user_name;
		this.user_phone = user_phone;
	}


	public UserDTO(String social_user_email, String user_name, String user_phone) {
		super();
		this.social_user_email = social_user_email;
		this.user_name = user_name;
		this.user_phone = user_phone;
	}
	
	public UserDTO(String pw, String email) {
        this.pw = pw;
        this.email = email;
    }

    public void setPw(String pw) {
        this.user_pw = pw;  // set user_pw to the provided pw value
    }

    public void setEmail(String email) {
        this.user_email = email;  // set user_email to the provided email value
    }
    
    public String getSocialUserEmail() {
        return social_user_email;
    }

    public void setSocialUserEmail(String social_user_email) {
        this.social_user_email = social_user_email;
    }

	public UserDTO(String user_email, String social_user_email, String user_name, String user_phone) {
		super();
		this.user_email = user_email;
		this.social_user_email = social_user_email;
		this.user_name = user_name;
		this.user_phone = user_phone;
	}
	
}
