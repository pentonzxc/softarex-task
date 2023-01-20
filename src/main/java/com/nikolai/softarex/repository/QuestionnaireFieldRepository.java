package com.nikolai.softarex.repository;


import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireFieldRepository extends
        JpaRepository<QuestionnaireField, Integer> {

    Optional<QuestionnaireField> findById(Integer id);
}
