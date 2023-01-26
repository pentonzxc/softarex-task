package com.nikolai.softarex.interfaces;


import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface QuestionnaireFieldService {
    void save(QuestionnaireField questionnaireField);

    void saveImmediately(QuestionnaireField questionnaireField);


    Optional<QuestionnaireField> findById(Integer id);

    Page<QuestionnaireField> findByUserId(Integer id, Pageable page);

    void update(QuestionnaireField questionnaireField);

    void remove(Integer id);

}
