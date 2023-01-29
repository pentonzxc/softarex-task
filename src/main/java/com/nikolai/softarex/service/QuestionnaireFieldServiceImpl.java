package com.nikolai.softarex.service;


import com.nikolai.softarex.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.repository.QuestionnaireFieldRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionnaireFieldServiceImpl implements
        QuestionnaireFieldService {

    private final QuestionnaireFieldRepository fieldRepository;

    public QuestionnaireFieldServiceImpl(QuestionnaireFieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }


    @Override
    public void save(QuestionnaireField questionnaireField) {
        fieldRepository.save(questionnaireField);
    }

    @Override
    public void saveImmediately(QuestionnaireField questionnaireField) {
        fieldRepository.saveAndFlush(questionnaireField);
    }

    @Override
    public Optional<QuestionnaireField> findById(Integer id) {
        return fieldRepository.findById(id);
    }


    @Override
    public Page<QuestionnaireField> findAllByUserId(Integer id, Pageable page) {
        return fieldRepository.findByUserIdOrderById(id, page);
    }


    @Override
    @Transactional
    public void update(QuestionnaireField target) {
        var fieldOpt = fieldRepository.findById(target.getId());
        QuestionnaireField source = target;

        if (fieldOpt.isPresent()) {
            source = fieldOpt.get();

            var newLabel = target.getLabel();
            var newProperties = target.getOptions();
            var newType = target.getType();
            var newRequired = target.isRequired();
            var newActive = target.isActive();

            if (newLabel != null) {
                source.setLabel(newLabel);
            }

            if (newProperties != null) {
                source.setOptions(newProperties);
            }

            if (newType != null) {
                source.setType(newType);
            }

            if (newRequired != null) {
                source.setRequired(newRequired);
            }

            if (newActive != null) {
                source.setActive(newActive);
            }

        }
        fieldRepository.save(source);
    }

    @Override
    public List<String> findAllLabels() {
        return fieldRepository
                .findAll(Sort.by(Sort.DEFAULT_DIRECTION, "id"))
                .stream()
                .map(QuestionnaireField::getLabel)
                .toList();
    }

    @Override
    public void remove(Integer id) {
        fieldRepository.deleteById(id);
    }

}
