package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.ChangePasswordRequest;
import com.nikolai.softarex.dto.UpdateProfileRequest;
import com.nikolai.softarex.service.SecurityService;
import com.nikolai.softarex.interfaces.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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

    private final SecurityService securityService;

    @Autowired
    public ProfileController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    @RequestMapping("/updateProfile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest profile) {
        userService.updateProfile(profile);
        return ResponseEntity.ok().build();
    }


    @RequestMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest passwords,
                                            HttpServletRequest request) throws MessagingException {
        securityService.changePassword(passwords, request);
        return ResponseEntity.ok().build();
    }

}
