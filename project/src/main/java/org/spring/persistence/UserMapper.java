package org.spring.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.spring.domain.InquiryDTO;
import org.spring.domain.RegisterDTO;
import org.spring.domain.UserDTO;

@Mapper
public interface UserMapper {

	// login
	@Select("SELECT * FROM user WHERE (login_type = #{login_type} AND user_email = #{user_email}) OR (login_type = #{login_type} AND social_user_email = #{user_email})")
	UserDTO getUser(UserDTO user);

	// @Select("SELECT COUNT(*) FROM user WHERE user_email = #{user_email} AND user_pw = #{user_pw}")
	public UserDTO validateUser(@Param("user_email") String user_email, @Param("user_pw") String user_pw);

	public void registerUser(RegisterDTO user);

	// naver
	@Insert("INSERT INTO user (social_user_email, user_name, user_phone, login_type) VALUES (#{user_email}, #{user_name}, #{user_phone}, #{login_type}) "
			+ "ON DUPLICATE KEY UPDATE user_name = VALUES(user_name),user_num = LAST_INSERT_ID(user_num)")
	void insertUser(UserDTO user);

	// google
	void insertOrUpdate(UserDTO userDTO);

	
	@Update("UPDATE user SET user_name = #{user_name}, user_phone = #{user_phone}, user_interest = #{user_interest} WHERE user_num = #{user_num}")
	void updateUserProfile(UserDTO user);

	// 아이디 중복 확인
	public boolean checkId(@Param("user_email") String user_email);

	// 이름 번호로 아이디 찾기
	public String searchId(@Param("user_name") String user_name, @Param("user_phone") String user_phone);

	// 임시 비밀번호 발급
	public UserDTO searchPw(@Param("user_email") String user_email);


	// 번호 중복 확인
	public int countByPhoneNumber(String user_phone);


	public UserDTO getUserByEmail(String email);

	public int updatePassword(UserDTO userDTO);

	
	// 소셜 로그인
	UserDTO findUserByEmail(String user_email);

	void updateGoogleUser(UserDTO userDTO);

	void insertGoogleUser(UserDTO userDTO);

	
	// kakao
	@Insert("INSERT INTO user (social_user_email, user_name, user_phone) VALUES (#{social_user_email}, #{user_name}, #{user_phone})")
	void insertKakaoUser(UserDTO kakaoUser);

	@Select(" SELECT user_num FROM users WHERE social_user_email = #{userEmail}")
	int getUnum(String userEmail);

	
	// 문의 폼
	@Insert("INSERT INTO user_inquiry (user_num, inquiry_purpose, inquiry_details, inquiry_regdate) "
			+ "VALUES (#{user_num}, #{inquiry_purpose}, #{inquiry_details}, NOW())")
	public void insertInquiry(InquiryDTO inquiryDTO);

	@Select("SELECT * FROM user_inquiry WHERE user_num = #{userNum} ORDER BY inquiry_regdate DESC LIMIT 5")
	public List<InquiryDTO> selectAllInquiries(int userNum);
	
	
	 // 관리자 문의 업데이트
	 List<InquiryDTO> getAllInquiries();
	 
	 void updateInquiryStatus(@Param("inquiry_num") int inquiryNum, @Param("inquiry_progress") String inquiryProgress);
}







