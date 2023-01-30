package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.ChangePasswordDto;
import com.nikolai.softarex.dto.UpdateProfileDto;
import com.nikolai.softarex.exception.UserNotFoundException;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.presenter.ContentResponse;
import com.nikolai.softarex.service.SecurityService;
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

    private final SecurityService securityService;

    @Autowired
    public ProfileController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileDto profile) {
        userService.updateProfile(profile);

        return new ContentResponse<>().response(
                HttpStatus.OK, null
        );
    }


    @RequestMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto passwords,
                                            HttpServletRequest request) throws MessagingException {
        securityService.changePassword(passwords, request);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> fullName(@PathVariable(name = "id", required = true) int user_id) {
        return new ContentResponse<>().response(
                HttpStatus.OK, userService.findById(user_id).orElseThrow(UserNotFoundException::new).getFullName()
        );
    }

}
