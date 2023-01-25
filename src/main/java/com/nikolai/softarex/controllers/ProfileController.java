package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.ChangePasswordDto;
import com.nikolai.softarex.dto.UpdateProfileDto;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.service.SecurityService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user/profile")
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
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileDto profile) {
//        userService.updateProfile(profile);
        return ResponseEntity.ok().build();
    }


    @RequestMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto passwords,
                                            HttpServletRequest request) throws MessagingException {
        securityService.changePassword(passwords, request);
        return ResponseEntity.ok().build();
    }

}
