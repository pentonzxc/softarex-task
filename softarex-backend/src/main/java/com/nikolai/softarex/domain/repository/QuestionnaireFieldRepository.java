package com.nikolai.softarex.domain.repository;


import com.nikolai.softarex.domain.entity.QuestionnaireField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionnaireFieldRepository extends
        JpaRepository<QuestionnaireField, Integer>, PagingAndSortingRepository<QuestionnaireField , Integer> {
    Optional<QuestionnaireField> findById(Integer id);

    Page<QuestionnaireField> findByUserIdOrderById(Integer user_id, Pageable pageable);
}
