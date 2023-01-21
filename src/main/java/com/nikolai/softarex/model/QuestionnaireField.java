package com.nikolai.softarex.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikolai.softarex.converter.StringListConverter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "softarex_task")
public class QuestionnaireField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Type getType() {
        return type;
    }

    public Boolean isRequired() {
        return isRequired;
    }

    public Boolean isActive() {
        return isActive;
    }

    public List<String> getOptions() {
        return options;
    }

    public User getUser() {
        return user;
    }

    private Boolean isRequired;

    private Boolean isActive;

    @Convert(converter = StringListConverter.class)
    private List<String> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void setOptions(List<String> properties) {
        this.options = properties;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public enum Type {
        SINGLE_LINE_TEXT,
        MULTILINE_TEXT,
        RADIO_BUTTON,
        CHECKBOX,
        COMBOBOX,
        DATE
    }
}
