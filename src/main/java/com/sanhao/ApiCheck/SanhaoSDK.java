package com.sanhao.ApiCheck;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class SanhaoSDK {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String apiUrl = "http://apiv2.sh.com";
    private static String apiChannel = "sanhao";
    private static String apiKey = "sanhao123";

    public static String doPost(Hashtable<String, Object> requestParams) {
        if (!requestParams.contains("timestamp")) {
            int timestamp = (int)(System.currentTimeMillis() / 1000);
            requestParams.put("timestamp", timestamp);
        }
        String encode = getEncode(requestParams);
        requestParams.put("encode", encode);

        MultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<>();
        requestHeaders.add("Channel-Id", apiChannel);

        MultiValueMap<String, String> requestBody = cleanParam(requestParams);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestBody, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

    public static MultiValueMap<String, String> cleanParam(Map<String, Object> requestParams) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        try {
            for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                String value = entry.getValue().toString();
                value = value.trim();
                value = URLEncoder.encode(value, "UTF-8");

                requestBody.add(entry.getKey(), value);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return requestBody;
    }

    public static String getEncode(Hashtable<String, Object> requestParams) {
        Map<String, Object> treeMap = new TreeMap<>(requestParams);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            builder.append(entry.getValue());
        }
        builder.append(apiKey);
        String tmp = builder.toString();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(tmp.getBytes());
            byte[] digest = md.digest();
            BigInteger no = new BigInteger(1, digest);
            tmp = no.toString(16);
            while (tmp.length() < 32) {
                tmp = "0" + tmp;
            }
            return tmp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tmp;
    }

    public static void main(String[] args) {
        Hashtable<String, Object> requestParams =new Hashtable<>();
        requestParams.put("act", "file_subject");
        requestParams.put("method", "lists");
        requestParams.put("limit", 0);
        requestParams.put("count", 20);

        try {
            String result = doPost(requestParams);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
