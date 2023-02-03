package com.nikolai.softarex.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class QuestionnaireResponseDto {
    private List<Object> data;
}
