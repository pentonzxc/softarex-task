package com.nikolai.softarex.repository;

import com.nikolai.softarex.model.QuestionnaireResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<QuestionnaireResponse, Integer> {

}
