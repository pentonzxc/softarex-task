package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.QuestionnaireDto;
import com.nikolai.softarex.dto.QuestionnaireResponseDto;
import com.nikolai.softarex.exception.QuestionnaireNotFoundException;
import com.nikolai.softarex.exception.UserNotFoundException;
import com.nikolai.softarex.interfaces.QuestionnaireService;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.model.QuestionnaireResponse;
import com.nikolai.softarex.service.SocketService;
import com.nikolai.softarex.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    private final UserService userService;

    private final SocketService socketService;

    private final EntityMapper<QuestionnaireResponse, QuestionnaireResponseDto> responseMapper;

    public QuestionnaireController(QuestionnaireService questionnaireService, UserService userService, SocketService socketService, EntityMapper<QuestionnaireResponse, QuestionnaireResponseDto> responseMapper) {
        this.questionnaireService = questionnaireService;
        this.userService = userService;
        this.socketService = socketService;
        this.responseMapper = responseMapper;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public QuestionnaireDto questionnaire(@PathVariable(name = "id", required = true)
                                          Integer id) {

        return questionnaireService.findById(id)
                .orElseThrow(() -> new QuestionnaireNotFoundException(
                        ExceptionMessageUtil.questionnaireNotFound(id)
                ));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionnaireResponse(@RequestBody List<Object> responseDto,
                                            @PathVariable(name = "id", required = true)
                                            Integer id) {
        var user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        var response = new QuestionnaireResponse();

        response.setData(responseDto);
        user.addQuestionnaireResponse(response);

        userService.save(user);
        socketService.notifyUser(user.getEmail());
    }
}
