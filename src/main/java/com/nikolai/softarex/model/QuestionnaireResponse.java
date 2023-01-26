package com.nikolai.softarex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikolai.softarex.converter.JsonArrayConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(schema = "softarex_task")
@Getter
public class QuestionnaireResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Convert(converter = JsonArrayConverter.class)
    private List<Object> data;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void setData(List<Object> data) {
        this.data = data;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
