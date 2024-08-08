//package org.spring.controller;
//
//import java.io.IOException;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.social.google.connect.GoogleConnectionFactory;
//import org.springframework.social.oauth2.AccessGrant;
//import org.springframework.social.oauth2.GrantType;
//import org.springframework.social.oauth2.OAuth2Operations;
//import org.springframework.social.oauth2.OAuth2Parameters;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.spring.domain.UserDTO;
//import org.spring.service.UserService;
//
//@Controller
//@RequestMapping("/social/*")
//public class GoogleLoginController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private GoogleConnectionFactory googleConnectionFactory;
//
//    @Autowired
//    private OAuth2Parameters googleOAuth2Parameters;
//
//    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
//    public String login(Model model) {
//        OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
//        String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
//        System.out.println("구글 로그인 URL: " + url);
//        model.addAttribute("google_login", url);
//        return "redirect:" + url; 
//    }
//
//    @RequestMapping(value = "/login_project/oauth2callback", method = { RequestMethod.GET, RequestMethod.POST })
//    public String googleCallback(UserDTO dto, Model model,
//            @RequestParam(value = "code", required = false) String code,
//            @RequestParam(value = "error", required = false) String error,
//            @RequestParam(value = "error_description", required = false) String errorDescription,
//            HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        // 에러가 발생한 경우
//        if (error != null) {
//            System.err.println("로그인 실패: " + error + " - " + errorDescription);
//            model.addAttribute("error", "로그인에 실패했습니다. 다시 시도해 주세요.");
//            return "redirect:/login"; // 로그인 페이지로 리디렉션
//        }
//
//        // 권한 코드가 제공된 경우
//        if (code != null) {
//            System.out.println("로그인 콜백 호출됨");
//            System.out.println("Authorization Code: " + code);
//
//            OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
//            AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(), null);
//
//            String accessToken = accessGrant.getAccessToken();
//            System.out.println("Access Token: " + accessToken);
//
//            Long expireTime = accessGrant.getExpireTime(); // 만료시 리프레시 토큰 발급
//            if (expireTime != null && expireTime < System.currentTimeMillis()) {
//                accessToken = accessGrant.getRefreshToken();
//                System.out.printf("Access token is expired. Refresh token = %s%n", accessToken);
//            }
//
//            if (accessToken != null) {
//                session.setAttribute("accessToken", accessToken);
//                session.setAttribute("loginUserID", accessToken);
//                session.setAttribute("loginType", "google");
//                System.out.println("세션에 토큰 저장");
//
//                try {
//                    // 사용자 프로필 정보 가져오기
//                    RestTemplate restTemplate = new RestTemplate();
//                    String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.add("Authorization", "Bearer " + accessToken);
//                    HttpEntity<String> entity = new HttpEntity<>(headers);
//                    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//                    String profile = responseEntity.getBody();
//                    System.out.println("User Profile: " + profile);
//
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    JsonNode rootNode = objectMapper.readTree(profile);
//
//                    // 사용자 정보를 UserDTO에 매핑
//                    UserDTO userProfile = new UserDTO();
//                    userProfile.setUser_email(rootNode.path("email").asText());
//                    userProfile.setUser_name(rootNode.path("name").asText());
//                    userProfile.setLogin_type("google"); // login_type 설정
//
//                    // 데이터베이스에 저장 또는 업데이트
//                    userService.insertOrUpdate(userProfile);
//
//                    // 세션에 사용자 정보 저장
//                    session.setAttribute("user_email", userProfile.getUser_email());
//                    session.setAttribute("user_name", userProfile.getUser_name());
//                    session.setAttribute("login_type", userProfile.getLogin_type());
//                    session.setAttribute("user_num", userProfile.getUser_num());
//                    model.addAttribute("userProfile", userProfile);
//
//                    return "redirect:/user"; // 성공 시 리턴 페이지
//                } catch (Exception e) {
//                    System.err.println("Google API 호출 중 오류 발생: " + e.getMessage());
//                    e.printStackTrace();
//                    model.addAttribute("error", "Google API 호출 중 오류가 발생했습니다.");
//                    return "error"; // 에러 페이지로 리디렉션
//                }
//            }
//        }
//
//        // 코드와 에러가 모두 없는 경우
//        return "redirect:/login"; // 로그인 페이지로 리디렉션
//    }
//
//    // 로그아웃 처리
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(HttpSession session, HttpServletResponse response) {
//        // 세션에서 액세스 토큰 가져오기
//        String accessToken = (String) session.getAttribute("accessToken");
//
//        if (accessToken != null) {
//            try {
//                // 토큰 해제 요청
//                RestTemplate restTemplate = new RestTemplate();
//                String revokeTokenUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
//                ResponseEntity<String> responseEntity = restTemplate.postForEntity(revokeTokenUrl, null, String.class);
//
//                if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                    System.out.println("토큰 해제됨: " + accessToken);
//                } else {
//                    System.out.println("토큰 해제 실패: " + accessToken);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 세션 무효화
//        session.invalidate();
//        System.out.println("로그아웃 세션 만료");
//        
//
//        // 로그아웃 후 리디렉션할 페이지 설정
//        return "redirect:/main";
//    }
//}
