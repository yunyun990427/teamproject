package org.spring.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.spring.domain.UserDTO;
import org.spring.service.EmailService;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class SearchPwController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/YM/pw_find")
    public String pwFind() {
        return "/YM/pw_find";  // Make sure this returns the view name
    }

    @GetMapping("/YM/pw_auth")
    public String pwAuth() {
        return "/YM/pw_auth";  // Make sure this returns the view name
    }

    @GetMapping("/YM/pw_new")
    public String pwNew() {
        return "/YM/pw_new";  // Make sure this returns the view name
    }

    @RequestMapping(value = "/YM/pw_auth.me", method = RequestMethod.POST)
    public String pwAuth(HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String email = request.getParameter("email");
        String name = request.getParameter("name");

        UserDTO userDTO = emailService.selectMember(email);
        if (userDTO != null) {
            Random r = new Random();
            int num = r.nextInt(999999);  // 랜덤 난수 설정

            if (userDTO.getUser_name().equals(name)) {
                session.setAttribute("email", userDTO.getUser_email());

                String title = "[동네26] 비밀번호변경 인증 이메일 입니다";
                String content = System.getProperty("line.separator") + "안녕하세요 회원님"
                        + System.getProperty("line.separator") + "동네26 비밀번호찾기(변경) 인증번호는 " + num + " 입니다."
                        + System.getProperty("line.separator");

                try {
                    emailService.sendEmail(email, title, content);
                    session.setAttribute("num", num);
                    session.setAttribute("email", email);
                    session.setAttribute("login_type", "basic");
                    redirectAttributes.addFlashAttribute("message", "이메일로 인증번호를 발송했습니다.");
                    return "redirect:/YM/pw_auth";
                } catch (Exception e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "이메일 발송에 실패했습니다.");
                    return "redirect:/YM/pw_find";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "다시 입력해주세요.");
                return "redirect:/YM/pw_find";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "존재하지 않는 계정입니다.");
            return "redirect:/YM/pw_find";
        }
    }

    @RequestMapping(value = "/YM/pw_set.me", method = RequestMethod.POST)
    public String pwSet(@RequestParam("emailCheck") String emailCheck,
                        @RequestParam("num") String num, RedirectAttributes redirectAttributes) throws IOException {

        if (emailCheck.equals(num)) {
            return "/YM/pw_new";
        } else {
            redirectAttributes.addFlashAttribute("error", "인증번호가 일치하지 않습니다.");
            return "redirect:/YM/pw_auth";
        }
    }

    @RequestMapping(value = "/YM/pw_new.me", method = RequestMethod.POST)public String pwNew(
    		
            @RequestParam("pw") String pw, 
            @RequestParam("pw2") String pw2, 
            @RequestParam("email") String email, 
            HttpSession session, 
            RedirectAttributes redirectAttributes) throws IOException {
        	System.out.println("여기1");
        if (!pw.equals(pw2)) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/YM/pw_new";
        }
        System.out.println("여기2");
        UserDTO userDTO = new UserDTO(pw, email);
        userDTO.setPw(pw);
        userDTO.setEmail(email);
        // 어떤 로그인 타입인지 구별하기 위해 사용
        userDTO.setLogin_type("basic");
        System.out.println("여기3");
        
        int result = userService.updatePassword(userDTO);
        System.out.println("리저트 : " + result);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/YM/pw_new";
        } else {
            
            return "redirect:/YM/pw_new";
        }
    }
}
