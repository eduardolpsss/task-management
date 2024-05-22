package com.desafio.backend.config;

import com.desafio.backend.entities.Task;
import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {

        Task t1 = new Task(null, "Curso Java", "Udemy", TaskStatus.PENDING);
        Task t2 = new Task(null, "Curso React", "Udemy", TaskStatus.CONCLUDED);
        Task t3 = new Task(null, "Curso PHP", "Udemy", TaskStatus.PENDING);

        taskRepository.saveAll(Arrays.asList(t1, t2, t3));
    }
}
