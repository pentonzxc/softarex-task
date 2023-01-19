package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.stereotype.Component;

import java.text.Format;


@Component
public class FieldMapper implements EntityMapper<QuestionnaireField , QuestionnaireFieldDto> {

    @Override
    public QuestionnaireField convertDtoToEntity(QuestionnaireFieldDto dto) {
        var field = new QuestionnaireField();
        field.setLabel(dto.getLabel());
        field.setType(dto.getType());
        field.setProperties(dto.getProperties());
        field.setActive(dto.isActive());
        field.setRequired(dto.isRequired());

        return field;
    }

    @Override
    public QuestionnaireFieldDto convertEntityToDto(QuestionnaireField entity) {
        return null;
    }
}
