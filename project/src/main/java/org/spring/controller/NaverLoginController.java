package org.spring.controller;

import org.spring.service.NaverLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@Controller
public class NaverLoginController {

    @Autowired
    private NaverLoginService naverLoginService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin == null) {
            isLogin = false;
        } else {
            isLogin = true;
        }
        session.setAttribute("isLogin", isLogin);
        return "login";
    }

    @GetMapping("/naverlogin")
    public String naverLogin(HttpSession session) throws Exception {
        String state = naverLoginService.generateState();
        session.setAttribute("state", state);
        String loginURL = naverLoginService.generateLoginURL(state);
        System.out.println(loginURL);
        return "redirect:" + loginURL;
    }
}
