package com.nikolai.softarex.controllers;


import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.exception.EmailNotFoundException;
import com.nikolai.softarex.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.model.QuestionnaireField;
import com.nikolai.softarex.model.QuestionnaireResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nikolai.softarex.util.ExceptionMessageUtil.emailNotFoundMsg;


@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;

    private final QuestionnaireFieldService fieldService;


    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    public UserController(UserService userService,
                          QuestionnaireFieldService fieldService,
                          EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper) {
        this.userService = userService;
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }


    @RequestMapping(value = "/{email}/addField", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addField(
            @RequestBody QuestionnaireFieldDto fieldDto,
            @PathVariable(value = "email", required = true) String email) {

        var user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));
        var field = fieldMapper.convertDtoToEntity(fieldDto);

        field.setUser(user);
        fieldService.saveImmediately(field);
        return field.getId();
    }


    @RequestMapping(value = "/{email}/fields", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionnaireFieldDto> getFields(@PathVariable(value = "email", required = true)
                                                 String email) {

        var user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));

        return user.getQuestionnaireFields()
                .stream()
                .map(fieldMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/{email}/responses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object getResponses(@PathVariable(value = "email", required = true)
                               String email) {
        var user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));

        return user.getQuestionnaireResponses()
                .stream()
                .map(QuestionnaireResponse::getData)
                .collect(Collectors.toList());
    }


}

