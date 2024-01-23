package com.ryangrillo.fida.todolist.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Todo {

    private Long id;

    private String description;

    private Boolean isCompleted;

    private String completedDate;
}
