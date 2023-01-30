package com.nikolai.softarex.controllers;

import com.nikolai.softarex.entity.QuestionnaireResponse;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.presenter.ContentResponse;
import com.nikolai.softarex.service.NotifyService;
import com.nikolai.softarex.service.QuestionnaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    private final UserService userService;

    private final NotifyService notifyService;

    private final EntityMapper<QuestionnaireResponse, List<Object>> responseMapper;

    public QuestionnaireController(QuestionnaireService questionnaireService,
                                   UserService userService,
                                   NotifyService notifyService,
                                   EntityMapper<QuestionnaireResponse, List<Object>> responseMapper) {
        this.questionnaireService = questionnaireService;
        this.userService = userService;
        this.notifyService = notifyService;
        this.responseMapper = responseMapper;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> questionnaire(@PathVariable(name = "id", required = true)
                                           Integer id) {
        return new ContentResponse<>().response(HttpStatus.OK, questionnaireService.questionnaireFromUserId(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createQuestionnaireResponse(@RequestBody List<Object> responseDto,
                                                         @PathVariable(name = "id", required = true)
                                                         Integer id) {
        var response = responseMapper.convertDtoToEntity(responseDto);
        questionnaireService.createQuestionnaireResponse(id, response);

        return new ContentResponse<>().response(HttpStatus.CREATED, null);
    }
}
