package com.nikolai.softarex.controllers;

import com.nikolai.softarex.mapper.FieldMapper;
import com.nikolai.softarex.repository.ResponseRepository;
import com.nikolai.softarex.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;

    private final ResponseRepository responseRepository;

    private final FieldMapper fieldMapper;

    @Autowired
    public UserController(UserService userService, ResponseRepository responseRepository, FieldMapper fieldMapper) {
        this.userService = userService;
        this.responseRepository = responseRepository;
        this.fieldMapper = fieldMapper;
    }

}
