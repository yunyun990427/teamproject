package org.spring.service;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LogoutService {
    private static final String CLIENT_ID = "cmuD8_P0HRDS5XYs3PqA"; // 애플리케이션 클라이언트 아이디값
    private static final String CLIENT_SECRET = "9DMiHuMfSJ"; // 애플리케이션 클라이언트 시크릿값

    public String performLogout(String accessToken) throws Exception {
        String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&access_token=" + accessToken
                + "&service_provider=NAVER";

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