package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String content;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    private LocalDateTime dateOfAddingTask = LocalDateTime.now();
    private LocalDateTime dueDateOfTask;
}
