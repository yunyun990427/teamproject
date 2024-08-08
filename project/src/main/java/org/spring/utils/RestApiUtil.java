package org.spring.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class RestApiUtil {

	public static <T> T ConnHttpGetType(String connUrl, HashMap<String, String> data,
			HashMap<String, String> headerData, Class<T> classType, boolean isXml) {

		try {
			StringBuilder urlBuilder = new StringBuilder(connUrl);

			int count = 0;

			for (String key : data.keySet()) {

				if (count == 0) {
					urlBuilder.append(
							"?" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8"));
				} else {
					urlBuilder.append(
							"&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8"));
				}
				count++;
			}
			
			String response;
            if (urlBuilder.toString().startsWith("https")) {
                response = httpsConn(urlBuilder.toString(), headerData);
            } else {
                response = httpConn(urlBuilder.toString(), headerData);
            }

            if (isXml) {
                return XmlUtil.parseXml(response, classType);
            } else {
                return JsonUtil.parseJson(response, classType);
            }

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static <T> T ConnHttpGetType2(String connUrl, HashMap<String, String> data,
            HashMap<String, String> headerData, Class<T> classType) {

    StringBuilder urlBuilder = new StringBuilder(connUrl);
    try {
        int count = 0;
        for (String key : data.keySet()) {
            if (count == 0) {
                urlBuilder.append("?" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8"));
            } else {
                urlBuilder.append("&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8"));
            }
            count++;
        }

        String jsonResponse;
        if (urlBuilder.toString().startsWith("https")) {
            jsonResponse = httpsConn(urlBuilder.toString(), headerData);
        } else {
            jsonResponse = httpConn(urlBuilder.toString(), headerData);
        }

        // JSON 응답 출력
        System.out.println("JSON Response: " + jsonResponse);

        return JsonUtil.parseJson(jsonResponse, classType);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

	private static String httpConn(String connUrl, HashMap<String, String> headerData) throws IOException {
		// System.out.println("HTTP connection to URL: " + connUrl);
		URL url = new URL(connUrl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		for (String key : headerData.keySet()) {
			conn.setRequestProperty(key, headerData.get(key));
		}

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		// System.out.println("HTTP response: "+sb.toString());
		return sb.toString();
	}
	
	

	private static String httpsConn(String connUrl, HashMap<String, String> headerData) throws IOException {
		// System.out.println("HTTPS connection to URL: " + connUrl);
		URL url = new URL(connUrl);

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		for (String key : headerData.keySet()) {
			conn.setRequestProperty(key, headerData.get(key));
		}

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		// System.out.println("HTTPS response: "+sb.toString());
		return sb.toString();
	}
	
	
}
