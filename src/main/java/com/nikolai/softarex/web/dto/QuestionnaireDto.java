package com.nikolai.softarex.web.dto;

import com.nikolai.softarex.domain.entity.QuestionnaireField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireDto {
    private List<QuestionnaireField> fieldList;
}
