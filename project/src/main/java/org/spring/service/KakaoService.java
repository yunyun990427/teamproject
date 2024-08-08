package org.spring.service;

import java.util.HashMap;

import org.spring.domain.UserDTO;
import org.spring.model.KakaoAuthResponse;
import org.spring.model.KakaoTokenResponse;
import org.spring.model.KakaoUserInfoResponse;
import org.spring.utils.RestApiUtil;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {
    private static final String APPKEY = "133dcac119e004b792bceaf4bca84d93";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    // 토큰 발급 기능
    public KakaoTokenResponse getToken(KakaoAuthResponse response) {
        HashMap<String, String> headerData = new HashMap<>();
        headerData.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HashMap<String, String> data = new HashMap<>();
        data.put("grant_type", "authorization_code");
        data.put("client_id", APPKEY);
        data.put("redirect_uri", "http://localhost:8090/kakao/oauth");
        data.put("code", response.getCode());
        data.put("client_secret", "Ag67qjLo75qC6Ev0dU7WV0YZhm8lVw8H"); // 추가된 부분

        KakaoTokenResponse result = null;
        try {
            result = RestApiUtil.ConnHttpGetType2(TOKEN_URL, data, headerData, KakaoTokenResponse.class);
           System.out.println("KakaoTokenResponse result11: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 사용자 정보 요청 기능
    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        HashMap<String, String> headerData = new HashMap<>();
        headerData.put("Authorization", "Bearer " + accessToken);

        KakaoUserInfoResponse result = null;
        try {
            result = RestApiUtil.ConnHttpGetType2(USER_INFO_URL, new HashMap<>(), headerData, KakaoUserInfoResponse.class);
          System.out.println("KakaoUserInfoResponse result222: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    // 사용자 정보를 UserDTO로 변환하는 메서드 추가
    public UserDTO createUserDTO(KakaoUserInfoResponse userInfo) {
        UserDTO user = new UserDTO();
        user.setUser_name(userInfo.getKakao_account().getProfile().getNickname());
        user.setUser_email(userInfo.getKakao_account().getEmail());
        user.setLogin_type("kakao");
        return user;
    }
}
