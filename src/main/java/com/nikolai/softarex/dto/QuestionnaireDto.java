package com.nikolai.softarex.dto;

import com.nikolai.softarex.model.QuestionnaireField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@AllArgsConstructor
@Data
public class QuestionnaireDto {
    private Integer id;
    private List<QuestionnaireField> fieldList;
}
