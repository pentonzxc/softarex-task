package com.nikolai.softarex.domain.repository;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireResponseRepository extends
        PagingAndSortingRepository<QuestionnaireResponse, Integer> , JpaRepository<QuestionnaireResponse , Integer> {

    Page<QuestionnaireResponse> findByUserId(int userId, Pageable pageable);

    void removeAllByUserId(int userId);


}
