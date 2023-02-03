package com.nikolai.softarex.web.controller;


import com.nikolai.softarex.domain.event.QuestionnaireFieldPublisher;
import com.nikolai.softarex.domain.event.RemoveAllResponsesEvent;
import com.nikolai.softarex.domain.entity.QuestionnaireField;
import com.nikolai.softarex.domain.interfaces.UserService;
import com.nikolai.softarex.web.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.web.exception.UserNotFoundException;
import com.nikolai.softarex.web.mapper.EntityMapper;
import com.nikolai.softarex.web.presenter.ContentResponse;
import com.nikolai.softarex.web.service.PageService;
import com.nikolai.softarex.web.util.ApiPath;
import com.nikolai.softarex.web.util.CommonUserMapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiPath.API_V1 + "/user")
public class UserController {

    private final UserService userService;
    private final PageService pageService;
    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    private final QuestionnaireFieldPublisher publisher;


    public UserController(UserService userService,
                          PageService pageService,
                          EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper, QuestionnaireFieldPublisher publisher) {
        this.userService = userService;
        this.pageService = pageService;
        this.fieldMapper = fieldMapper;
        this.publisher = publisher;
    }


    @RequestMapping(
            value = "/{id}/addField",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addField(
            @RequestBody QuestionnaireFieldDto fieldDto,
            @PathVariable(name = "id", required = true) int userId
    ) {
        publisher.publishEvent(new RemoveAllResponsesEvent(userId));
        var field = fieldMapper.convertDtoToEntity(fieldDto);

        return new ContentResponse<>().response(HttpStatus.CREATED, userService.addField(userId, field).getId());
    }


    @RequestMapping(value = "/{id}/fields", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> fields(
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
    public ResponseEntity<?> responses(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            @PathVariable(name = "id", required = true) int userId
    ) {

        return new ContentResponse<>().response(
                HttpStatus.OK, pageService.createResponsePage(page, size, userId)
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> user(@PathVariable(name = "id", required = true) int userId) {
        return new ContentResponse<>().response(
                HttpStatus.OK, CommonUserMapperUtil.userEntityToProfileDto(
                        userService.findById(userId).orElseThrow(UserNotFoundException::new)
                )
        );
    }





}

