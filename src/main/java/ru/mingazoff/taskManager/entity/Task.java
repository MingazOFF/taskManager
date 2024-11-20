package ru.mingazoff.taskManager.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This is a class that describes a task and a table in the database.
 */
@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "user_id")
    private int userId;
}
