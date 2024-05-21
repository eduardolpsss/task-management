package com.desafio.backend.entities;

import com.desafio.backend.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String description;

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant creationDate;

    @Setter
    @Getter
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

    public TaskStatus getTaskStatus() {
        return TaskStatus.valueOf(taskStatus);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        if (taskStatus != null) {
            this.taskStatus = taskStatus.getCode();
        }
    }
}
