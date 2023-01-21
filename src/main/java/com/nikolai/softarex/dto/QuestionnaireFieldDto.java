package com.nikolai.softarex.dto;

import com.nikolai.softarex.model.QuestionnaireField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireFieldDto {

    private Integer id;

    private String label;

    private QuestionnaireField.Type type;

    private boolean isRequired;

    private boolean isActive;

    private List<String> options;
}
