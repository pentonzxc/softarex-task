package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.QuestionnaireResponseDto;
import com.nikolai.softarex.model.QuestionnaireResponse;
import org.springframework.stereotype.Component;


@Component
public class QuestionnaireResponseMapper
        implements EntityMapper<QuestionnaireResponse , QuestionnaireResponseDto> {


    @Override
    public QuestionnaireResponse convertDtoToEntity(QuestionnaireResponseDto dto) {
        var entity = new QuestionnaireResponse();
        entity.setData(dto.getData());

        return entity;
    }

    @Override
    public QuestionnaireResponseDto convertEntityToDto(QuestionnaireResponse entity) {
        var dto = new QuestionnaireResponseDto();
        dto.setData(entity.getData());

        return dto;
    }
}
