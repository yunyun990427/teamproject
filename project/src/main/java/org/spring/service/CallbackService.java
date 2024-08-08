package org.spring.service;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class CallbackService {
    private static final String CLIENT_ID = "cmuD8_P0HRDS5XYs3PqA"; // 애플리케이션 클라이언트 아이디값
    private static final String CLIENT_SECRET = "9DMiHuMfSJ"; // 애플리케이션 클라이언트 시크릿값
    private static final String REDIRECT_URI = "http://localhost:8090/callback"; // 애플리케이션 콜백 URL

    public String getAccessToken(String code, String state) throws Exception {
        String encodedRedirectURI = URLEncoder.encode(REDIRECT_URI, "UTF-8");
        String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + encodedRedirectURI
                + "&code=" + code
                + "&state=" + state;

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        StringBuilder res = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();

        if (responseCode == 200) {
            return res.toString();
        } else {
            throw new Exception("Error: " + res.toString());
        }
    }
}
