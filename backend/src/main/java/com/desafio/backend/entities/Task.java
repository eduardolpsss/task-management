package com.desafio.backend.entities;

import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.entities.exceptions.TitleLengthException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant creationDate;
    private Integer taskStatus;

    public Task() {
        this.creationDate = Instant.now();
    }

    public Task(Long id, String title, String description, TaskStatus taskStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = Instant.now();
        setTaskStatus(taskStatus);
    }

    public void setTitle(String title) {
        if (title == null || title.length() < 5) {
            throw new TitleLengthException("Title must have at least 5 characters.");
        }
        this.title = title;
    }

    public TaskStatus getTaskStatus() {
        return TaskStatus.valueOf(taskStatus);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        if (taskStatus != null) {
            this.taskStatus = taskStatus.getCode();
        }
    }
}