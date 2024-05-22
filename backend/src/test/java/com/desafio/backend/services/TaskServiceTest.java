package com.desafio.backend.services;

import com.desafio.backend.entities.Task;
import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.repositories.TaskRepository;
import com.desafio.backend.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    @InjectMocks
    private TaskService service;
    @Mock
    private TaskRepository repository;
    private Task task;
    private Optional<Task> optionalTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTask();
    }

    @Test
    void whenfindAllTasksSortedByCreationDateReturnListOfTasks() {
        Mockito.when(repository.findAllByOrderByCreationDateAsc()).thenReturn(List.of(task));

        List<Task> response = service.findAllTasksSortedByCreationDate();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(Task.class, response.get(0).getClass());
        Assertions.assertEquals(1L, response.get(0).getId());
        Assertions.assertEquals("Curso Java", response.get(0).getTitle());
        Assertions.assertEquals("Udemy", response.get(0).getDescription());
        Assertions.assertEquals(TaskStatus.PENDING, response.get(0).getTaskStatus());
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalTask);

        Task response = service.findById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Task.class, response.getClass());
        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals("Curso Java", response.getTitle());
        Assertions.assertEquals("Udemy", response.getDescription());
        Assertions.assertEquals(TaskStatus.PENDING, response.getTaskStatus());
    }

    @Test
    void whenFindByIdThenReturnResourceNotFoundException() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException("Object not found."));

        try {
            service.findById(1L);
        } catch (Exception ex) {
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Object not found.", ex.getMessage());
        }
    }

    @Test
    void whenInsertThenSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(task);

        Task response = service.insert(task);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Task.class, response.getClass());
        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals("Curso Java", response.getTitle());
        Assertions.assertEquals("Udemy", response.getDescription());
        Assertions.assertEquals(TaskStatus.PENDING, response.getTaskStatus());
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    private void startTask() {
        task = new Task(1L, "Curso Java", "Udemy", TaskStatus.PENDING);
        optionalTask = Optional.of(new Task(1L, "Curso Java", "Udemy", TaskStatus.PENDING));
    }
}