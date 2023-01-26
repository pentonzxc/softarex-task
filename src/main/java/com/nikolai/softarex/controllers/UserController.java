package com.nikolai.softarex.controllers;


import com.nikolai.softarex.dto.PageDto;
import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.exception.UserNotFoundException;
import com.nikolai.softarex.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.interfaces.QuestionnaireResponseService;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.model.QuestionnaireField;
import com.nikolai.softarex.model.QuestionnaireResponse;
import com.nikolai.softarex.util.PageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;

    private final QuestionnaireFieldService fieldService;

    private final QuestionnaireResponseService responseService;


    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    public UserController(UserService userService,
                          QuestionnaireFieldService fieldService,
                          QuestionnaireResponseService responseService,
                          EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper) {
        this.userService = userService;
        this.fieldService = fieldService;
        this.responseService = responseService;
        this.fieldMapper = fieldMapper;
    }


    @RequestMapping(
            value = "/{id}/addField",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addField(
            @RequestBody QuestionnaireFieldDto fieldDto,
            @PathVariable(value = "id", required = true) int userId
    ) {

        var user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        var field = fieldMapper.convertDtoToEntity(fieldDto);

        field.setUser(user);
        fieldService.saveImmediately(field);
        return field.getId();
    }


    @RequestMapping(value = "/{id}/fields", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PageDto<?> getFields(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            @PathVariable(name = "id", required = true) int userId
    ) {
        var pageable = PageUtil.createPage(page, size);
        var contentWrapper = fieldService.findByUserId(userId, pageable);
        var fields = contentWrapper.stream().map(fieldMapper::convertEntityToDto).toList();


        return new PageDto<>(contentWrapper.getTotalPages(), contentWrapper.getTotalElements() , fields);
    }


    @RequestMapping(value = "/{id}/responses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PageDto<?> getResponses(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            @PathVariable(value = "id", required = true) int userId
    ) {

        var pageable = PageUtil.createPage(page, size);
        var contentWrapper = responseService.findByUserId(userId, pageable);
        var responses = contentWrapper.stream().map(QuestionnaireResponse::getData).toList();

        return new PageDto<>(contentWrapper.getTotalPages(), contentWrapper.getTotalElements() , responses);
    }


}

