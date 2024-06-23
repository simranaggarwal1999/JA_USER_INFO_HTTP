package org.sita.controller;

import lombok.extern.slf4j.Slf4j;
import org.sita.service.UserRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/appName")
public class UserRequestController {

    @Autowired
    Environment env;

    @Autowired
    UserRequestHandler userRequestHandler;

    @GetMapping("/userDetail")
    public ResponseEntity<String> getUserDetail(@RequestParam String user) {
        if (env.containsProperty(user)) {
            String workstation = env.getProperty(user);
            RestTemplate restTemplate = new RestTemplate();
            return userRequestHandler.handle(user,workstation,restTemplate);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}