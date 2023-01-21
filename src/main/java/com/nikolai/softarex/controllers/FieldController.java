package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.exception.FieldNotFoundException;
import com.nikolai.softarex.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.mapper.FieldMapper;
import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/field")
public class FieldController {

    private final UserService userService;

    private final QuestionnaireFieldService fieldService;

    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    @Autowired
    public FieldController(UserService userService,
                           QuestionnaireFieldService fieldService,
                           FieldMapper fieldMapper) {
        this.userService = userService;
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void edit(@RequestBody QuestionnaireFieldDto fieldDto) {
        var field = fieldMapper.convertDtoToEntity(fieldDto);

        fieldService.update(field);
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value = "id", required = true) Integer id) {
        fieldService.remove(id);
    }

    @RequestMapping(value = "/editActive/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void disable(@PathVariable(value = "id", required = true) Integer id) {
        var field = fieldService.findById(id).orElseThrow(FieldNotFoundException::new);
        field.setActive(!field.isActive());
    }


}
