package com.isep.dfe.todos.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
// Cette annotation spécifie que seules les propriétés non nulles doivent être incluses dans la sortie.
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean completed;

}

