package com.nikolai.softarex.web.controller;

import com.nikolai.softarex.domain.entity.UserPasswordChange;
import com.nikolai.softarex.domain.interfaces.UserService;
import com.nikolai.softarex.security.service.ChangePasswordService;
import com.nikolai.softarex.security.service.SecurityService;
import com.nikolai.softarex.web.dto.ChangePasswordDto;
import com.nikolai.softarex.web.dto.UpdateProfileDto;
import com.nikolai.softarex.web.exception.UserNotFoundException;
import com.nikolai.softarex.web.presenter.ContentResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user/profile")
@Slf4j
public class ProfileController {

    private final UserService userService;


    private final ChangePasswordService changePasswordService;


    @Autowired
    public ProfileController(UserService userService,
                             ChangePasswordService changePasswordService) {
        this.userService = userService;
        this.changePasswordService = changePasswordService;
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileDto profile) {
        userService.updateProfile(profile);

        return new ContentResponse<>().response(
                HttpStatus.OK, null
        );
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto passwords,
                                            @RequestParam(name = "id", required = true) int user_id,
                                            HttpServletRequest request) throws MessagingException {
        changePasswordService.changePassword(
                userService.findById(user_id).orElseThrow(UserNotFoundException::new),
                passwords,
                request
        );


        return new ContentResponse<>().response(HttpStatus.OK, null);
    }


    @RequestMapping(value = "/changePassword/verify", method = RequestMethod.GET)
    public ResponseEntity<?> verifyPasswordChange(@RequestParam(name = "code", required = true)
                                                  String verificationCode) {
        UserPasswordChange passwordChange = changePasswordService.verifyChangePassword(verificationCode);
        userService.changePassword(passwordChange.getUser(), passwordChange);

        return new ContentResponse<>().response(HttpStatus.OK, null);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> fullName(@PathVariable(name = "id", required = true) int user_id) {
        return new ContentResponse<>().response(
                HttpStatus.OK, userService.findById(user_id).orElseThrow(UserNotFoundException::new).getFullName()
        );
    }

}
