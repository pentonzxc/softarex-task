package com.nikolai.softarex.repository;

import com.nikolai.softarex.entity.QuestionnaireResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireResponseRepository extends
        PagingAndSortingRepository<QuestionnaireResponse, Integer>{

    Page<QuestionnaireResponse> findByUserId(int user_id, Pageable pageable);



}
