package org.spring.controller;

import org.spring.domain.UserDTO;
import org.spring.service.CallbackService;
import org.spring.service.LogoutService;
import org.spring.service.NaverLoginService;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/callback")
public class CallbackController {

    @Autowired
    private CallbackService callbackService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private NaverLoginService naverLoginService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "error_description", required = false) String errorDescription,
                           HttpSession session) throws IOException {
        if (error != null) {
            // 에러가 발생한 경우
            session.setAttribute("isLogin", false);
            return "redirect:/login";
        }

        String storedState = (String) session.getAttribute("state");
        if (storedState == null || !storedState.equals(state)) {
            session.setAttribute("isLogin", false);
            return "redirect:/login";
        }

        try {
            String accessTokenResponse = callbackService.getAccessToken(code, state);
            String accessToken = extractAccessToken(accessTokenResponse);
            session.setAttribute("accessToken", accessToken);

            String userProfile = naverLoginService.getUserProfile(accessToken);
            session.setAttribute("loginUserID", userProfile);
            session.setAttribute("loginType", "naver");
            // 사용자 정보를 UserDTO 객체에 담기
            UserDTO user = naverLoginService.extractUserInfo(userProfile, session);
            
            //userService.saveUser(user);
            userService.insertOrUpdate(user);
            return "redirect:/user";
        } catch (Exception e) {
            session.setAttribute("isLogin", false);
            return "redirect:/user";
        }
    }

    @GetMapping("/performLogout")
    public String performLogout(HttpSession session) throws IOException {
        String accessToken = (String) session.getAttribute("accessToken");
        if (accessToken == null) {
            session.setAttribute("isLogin", false);
            return "redirect:/login";
        }

        try {
            logoutService.performLogout(accessToken);
            session.invalidate(); // 세션 무효화
            return "redirect:/main";
        } catch (Exception e) {
            session.invalidate(); // 예외 발생 시에도 세션 무효화
            return "redirect:/login";
        }
    }

    private String extractAccessToken(String accessTokenResponse) {
        int startIndex = accessTokenResponse.indexOf("\"access_token\":\"") + 16;
        int endIndex = accessTokenResponse.indexOf("\"", startIndex);
        return accessTokenResponse.substring(startIndex, endIndex);
    }
}
