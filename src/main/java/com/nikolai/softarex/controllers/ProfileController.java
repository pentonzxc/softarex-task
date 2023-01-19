package com.nikolai.softarex.controllers;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.nikolai.softarex.dto.ChangePasswordRequest;
import com.nikolai.softarex.dto.UpdateProfileRequest;
import com.nikolai.softarex.service.EmailService;
import com.nikolai.softarex.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user/profile")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
public class ProfileController {

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public ProfileController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @RequestMapping("/updateProfile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest profile) {
        userService.update(profile);
        return ResponseEntity.ok().build();
    }


    @RequestMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest passwords){
        return null;
    }



}
