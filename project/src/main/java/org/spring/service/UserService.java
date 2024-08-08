package org.spring.service;

import java.util.List;

import org.spring.domain.InquiryDTO;
import org.spring.domain.RegisterDTO;
import org.spring.domain.UserDTO;
import org.spring.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserDTO login(String user_email, String user_pw) {
        return userMapper.validateUser(user_email,user_pw) ;
    }

    public void register(RegisterDTO user) {
        userMapper.registerUser(user);
    }

    //naver
    public void saveUser(UserDTO user) {
        userMapper.insertUser(user);
    }


    
    public void insertOrUpdate(UserDTO userDTO) {
        UserDTO existingUser = userMapper.findUserByEmail(userDTO.getUser_email());
        
        if (existingUser != null) {
            userMapper.updateGoogleUser(userDTO);
        } else {
            userMapper.insertGoogleUser(userDTO);
        }
    }
    

    // 아이디 중복 확인
    public boolean isIdDuplicated(String user_email) {
        log.info("아이디 중복 확인");
        return userMapper.checkId(user_email);
    }

    // 이름 번호로 아이디 찾기
    public String searchId(String user_name, String user_phone) {
        log.info("아이디 찾기");
        return userMapper.searchId(user_name, user_phone);
    }
    
    public UserDTO getUserInfo(UserDTO user) {
		return userMapper.getUser(user);
	}

	public void updateUserProfile(UserDTO user) {
		userMapper.updateUserProfile(user);		
	}


	// 중복 번호 찾기
	 public boolean isPhoneNumberDuplicated(String user_phone) {
	        return userMapper.countByPhoneNumber(user_phone) > 0;
	    }
	 
	// 비밀번호 변경
	 public int updatePassword(UserDTO userDTO) {
		 log.info("업데이트 전 비밀번호: " + userDTO);
		 
	     log.info("아이디: " + userDTO.getEmail() + 
	    		 " 새 비밀번호: " + userDTO.getUser_pw());
		    
		    int updateResult = userMapper.updatePassword(userDTO);
		    System.out.println("업데이트 결과: " + updateResult);
		    
		    return updateResult;
		
	 }

	 
	 //카카오
	 public void saveKakaoUser(UserDTO kakaoUser) {
         System.out.println("들어오니?"+kakaoUser);
         UserDTO existingUser = userMapper.getUser(kakaoUser);
         if (existingUser == null) { 
         userMapper.insertKakaoUser(kakaoUser);
     }else {
         userMapper.updateUserProfile(kakaoUser);
     }
 }
	 
	 public int getUserNum(String userEmail) {
         return userMapper.getUnum(userEmail);
     }
	
	 
	// 문의 폼
	@Transactional
	public void saveInquiry(InquiryDTO inquiryDTO) {
		userMapper.insertInquiry(inquiryDTO);
	}
	
	public List<InquiryDTO> getInquiries(int userNum) {
		List<InquiryDTO> inquiries = userMapper.selectAllInquiries(userNum);
	    System.out.println("Inquiries: " + inquiries);
	    return inquiries;
	}
	
	public List<InquiryDTO> getAllInquiries() {
        return userMapper.getAllInquiries();
    }
	 public void updateInquiryStatus(int inquiryNum, String inquiryProgress) {
		 userMapper.updateInquiryStatus(inquiryNum, inquiryProgress);
	    }
	
	
}
