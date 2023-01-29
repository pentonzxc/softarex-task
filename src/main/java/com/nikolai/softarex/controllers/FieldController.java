package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.mapper.FieldMapper;
import com.nikolai.softarex.presenter.ContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/field")
public class FieldController {

    private final QuestionnaireFieldService fieldService;

    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;

    @Autowired
    public FieldController(QuestionnaireFieldService fieldService,
                           FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> edit(@RequestBody QuestionnaireFieldDto fieldDto) {
        var field = fieldMapper.convertDtoToEntity(fieldDto);
        fieldService.update(field);

        return new ContentResponse<>().response(HttpStatus.OK, null);
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> remove(@PathVariable(value = "id", required = true) Integer id) {
        fieldService.remove(id);

        return new ContentResponse<>().response(HttpStatus.OK, null);
    }


}
