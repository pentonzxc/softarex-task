package com.nikolai.softarex.interfaces;


import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.model.QuestionnaireField;

import java.util.List;
import java.util.Optional;


public interface QuestionnaireFieldService {
    void save(QuestionnaireField questionnaireField);

    void saveImmediately(QuestionnaireField questionnaireField);

    List<QuestionnaireField> findAll();

    Optional<QuestionnaireField> findById(Integer id);

    void update(QuestionnaireField questionnaireField);

    void remove(Integer id);

}
