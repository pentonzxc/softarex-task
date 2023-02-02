package com.nikolai.softarex.web.controller;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import com.nikolai.softarex.web.exception.QuestionnaireNotFoundException;
import com.nikolai.softarex.web.mapper.EntityMapper;
import com.nikolai.softarex.web.presenter.ContentResponse;
import com.nikolai.softarex.web.service.QuestionnaireService;
import com.nikolai.softarex.web.util.ApiPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPath.API_V1 + "/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;


    private final EntityMapper<QuestionnaireResponse, List<Object>> responseMapper;

    public QuestionnaireController(QuestionnaireService questionnaireService,
                                   EntityMapper<QuestionnaireResponse, List<Object>> responseMapper) {
        this.questionnaireService = questionnaireService;
        this.responseMapper = responseMapper;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> questionnaire(@PathVariable(name = "id", required = true)
                                           Integer id) {
        try {
            var questionnaireDto = questionnaireService.questionnaireFromUserId(id);
            return new ContentResponse<>().response(HttpStatus.OK, questionnaireDto);
        } catch (QuestionnaireNotFoundException ex) {
            return new ContentResponse<>().response(HttpStatus.BAD_REQUEST, null);
        }
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
