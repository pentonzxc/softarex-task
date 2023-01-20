package com.nikolai.softarex.dto;

import com.nikolai.softarex.model.QuestionnaireField;
import lombok.Data;

import java.util.List;


@Data
public class QuestionnaireFieldDto {

    private Integer id;

    private String label;

    private QuestionnaireField.Type type;

    private boolean isRequired;

    private boolean isActive;

    private List<String> properties;
}
