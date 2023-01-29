package com.nikolai.softarex.controllers;


import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.exception.UserNotFoundException;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.presenter.ContentResponse;
import com.nikolai.softarex.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nikolai.softarex.util.ExceptionMessageUtil.userNotFoundMsg;


@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;
    private final PageService pageService;
    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    public UserController(UserService userService,
                          PageService pageService,
                          EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper) {
        this.userService = userService;
        this.pageService = pageService;
        this.fieldMapper = fieldMapper;
    }


    @RequestMapping(
            value = "/{id}/addField",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addField(
            @RequestBody QuestionnaireFieldDto fieldDto,
            @PathVariable(value = "id", required = true) int userId
    ) {

        var user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userNotFoundMsg(userId)));
        var field = fieldMapper.convertDtoToEntity(fieldDto);

        return new ContentResponse<>().response(HttpStatus.CREATED, userService.addField(user, field).getId());
    }


    @RequestMapping(value = "/{id}/fields", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFields(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            @PathVariable(name = "id", required = true) int userId
    ) {
        return new ContentResponse<>().response(
                HttpStatus.OK, pageService.createFieldPage(page, size, userId)
        );
    }


    @RequestMapping(value = "/{id}/responses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getResponses(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            @PathVariable(value = "id", required = true) int userId
    ) {

        return new ContentResponse<>().response(
                HttpStatus.OK, pageService.createResponsePage(page, size, userId)
        );
    }


}

