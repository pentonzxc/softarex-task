package com.nikolai.softarex.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.nikolai.softarex.converter.StringListConverter;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.TypeAlias;

import java.sql.CallableStatement;
import java.util.List;

@Entity
@Getter
@Table(schema = "softarex_task")
public class QuestionnaireField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private boolean isRequired;

    private boolean isActive;

    @Convert(converter = StringListConverter.class)
    private List<String> properties;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setProperties(List<String> properties) {
        this.properties = properties;
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
