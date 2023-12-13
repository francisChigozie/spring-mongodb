package com.fcwebpp.springmongodb.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "todos")
public class ToDodto {
    @Id
    private String id;

    @NotNull(message = "Todo must not empty")
    private String todo;

    @NotNull(message = "Description must not empty")
    private String description;

    @NotNull(message = "Completed must not empty")
    private Boolean completed;

    private Date createAt;
    private Date updatedAt;
}
