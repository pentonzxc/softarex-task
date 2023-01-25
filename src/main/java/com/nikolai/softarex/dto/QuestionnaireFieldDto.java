package com.nikolai.softarex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikolai.softarex.model.QuestionnaireField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireFieldDto {

    private Integer id;

    private String label;

    private QuestionnaireField.Type type;


    private boolean isRequired;

    private boolean isActive;

    private String options;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public QuestionnaireField.Type getType() {
        return type;
    }

    public void setType(QuestionnaireField.Type type) {
        this.type = type;
    }


    @JsonProperty(value = "required")
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }


    @JsonProperty(value = "active")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
