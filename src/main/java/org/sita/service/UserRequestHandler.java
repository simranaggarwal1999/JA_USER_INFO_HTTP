package org.sita.service;

import org.sita.dao.UserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UserRequestHandler {

    @Value("${credentials.username}")
    private String username;

    @Value("${credentials.password}")
    private String password;

    @Value("${external.service.url}")
    private String externalServiceUrl;

    public ResponseEntity<String> handle(String user,String workstation,RestTemplate restTemplate){
        UserDetail userDetail = new UserDetail(user, workstation, "Success", "user exist in database and has access to given workstation");

        HttpHeaders headers = createHeaders(username, password);
        HttpEntity<UserDetail> requestHttpEntity=new HttpEntity<>(userDetail,headers);

        ResponseEntity<String> postResponse = restTemplate.exchange(externalServiceUrl, HttpMethod.POST,requestHttpEntity,String.class);

        if (postResponse.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>("User Info added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to post data to appName2", postResponse.getStatusCode());
        }
    }

    private HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }


}
