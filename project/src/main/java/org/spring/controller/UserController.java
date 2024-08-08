package org.spring.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import config.MailConfig;

import org.spring.domain.InquiryDTO;
import org.spring.domain.UserDTO;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private MailConfig mailConfig; //문의폼 mailConfig
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUser(HttpSession session) {
        String user_name = (String) session.getAttribute("user_name");
        String user_email = (String) session.getAttribute("user_email");
        String login_type = (String) session.getAttribute("login_type");
        
        UserDTO user = new UserDTO();
        user.setUser_name(user_name);
        user.setUser_email(user_email);
        user.setLogin_type(login_type);
        System.out.println("유저 네임 :" + user_name);
        System.out.println("유저 이메일 :" + user_email);
        System.out.println("로그인 타입 :" + login_type);
        if (user_email == null || user_name.isEmpty()) {
            // user_name이 세션에 없을 경우의 처리
            System.out.println("User name is not in session");
            return "redirect:/login"; // 에러 페이지로 리디렉션
        }

        System.out.println(user_name);
        UserDTO userInfo = userService.getUserInfo(user);
        
        if (userInfo != null) {
            //System.out.println(userInfo.getUser_num());
            session.setAttribute("user_num", userInfo.getUser_num());
            System.out.println(userInfo.getUser_num());
            session.setAttribute("user_info", userInfo);
        } else {
            // 유저 정보를 찾지 못했을 경우의 처리
            System.out.println("User not found");
            return "redirect:/error"; // 에러 페이지로 리디렉션
        }
        return "redirect:/main"; // 올바른 경로인지 확인
    }
    
    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, 
                                @RequestParam("user_num") Integer userNum,
                                @RequestParam("user_name") String userName,
                                @RequestParam("user_email") String userEmail,
                                @RequestParam("user_phone") String userPhone,
                                @RequestParam("user_interest") String userInterest,
                                @RequestParam(value = "social_user_email", required = false) String socialUserEmail
                                ) {
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user != null && user.getUser_num().equals(userNum)) {
            user.setUser_name(userName);
            user.setUser_phone(userPhone);
            user.setUser_interest(userInterest);
            
            if (userEmail != null && !userEmail.isEmpty()) {
                user.setUser_email(userEmail);
            } else if (socialUserEmail != null && !socialUserEmail.isEmpty()) {
                user.setUser_email(socialUserEmail);
            }
            
            userService.updateUserProfile(user);
            session.setAttribute("user_info", user);
            System.out.println("세션정보: " + user);
            System.out.println("성공");
        } else {
        	System.out.println("실패");
        }
        return "myPage";
    }
    
    @GetMapping("/myPage")
    public String showMyPage(HttpSession session, Model model) {
    	UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user_info", user);
        return "myPage";
    }

    @PostMapping("/formSubmit")
    @ResponseBody
    public void submitForm(@RequestParam Map<String, String> request,
            MultipartHttpServletRequest multiRequest, HttpSession session) {
        try {
            // 사용자 이메일 설정
            request.put("userEmail", (String) session.getAttribute("user_email"));
            int userNum = (int) session.getAttribute("user_num");
            String purpose = request.get("purpose");
            String details = request.get("details");

            // db에 문의내역 추가
            InquiryDTO inquiryDTO = new InquiryDTO();
            inquiryDTO.setUser_num(userNum);
            inquiryDTO.setInquiry_purpose(purpose);
            inquiryDTO.setInquiry_details(details);
            userService.saveInquiry(inquiryDTO);
            
            // 파일 업로드 처리
            MultipartFile file = multiRequest.getFile("attachment");
            InputStream fileContent = null;
            String fileName = null;
            if (file != null && !file.isEmpty()) {
                fileContent = file.getInputStream();
                fileName = file.getOriginalFilename();
            }
            mailConfig.sendEmail(request, fileContent, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping("/inquiries")
    @ResponseBody
    public ResponseEntity<List<InquiryDTO>> getInquiries(@RequestParam("userNum") int userNum) {
        try {
            List<InquiryDTO> inquiries = userService.getInquiries(userNum);
            return ResponseEntity.ok(inquiries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("message", "서버에서 오류가 발생했습니다."));
    }
}
