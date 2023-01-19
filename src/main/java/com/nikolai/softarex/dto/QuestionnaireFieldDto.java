package com.nikolai.softarex.dto;

import com.nikolai.softarex.model.QuestionnaireField;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Map;


@Data
public class QuestionnaireFieldDto {
    private String label;
    private QuestionnaireField.Type type;
    private boolean isRequired;

    private boolean isActive;

    private List<String> properties;
}
