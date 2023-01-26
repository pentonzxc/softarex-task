package com.nikolai.softarex.dto;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
public class QuestionnaireResponseDto {
    private List<Object> data;
}
