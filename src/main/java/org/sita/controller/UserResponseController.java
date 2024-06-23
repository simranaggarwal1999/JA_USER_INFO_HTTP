package org.sita.controller;

import org.sita.dao.UserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appName2")
public class UserResponseController {

    @PostMapping(path = "/addUserInfo")
    public ResponseEntity<String> createUser(@RequestBody UserDetail newUser) {
            return ResponseEntity.status(HttpStatus.OK).body("User info added");
    }
}
