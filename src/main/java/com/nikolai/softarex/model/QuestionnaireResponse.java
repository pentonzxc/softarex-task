package com.nikolai.softarex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.nikolai.softarex.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(schema = "softarex_task")
@Getter
public class QuestionnaireResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Convert(converter = JsonConverter.class)
    private Map<String, Object> data;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
