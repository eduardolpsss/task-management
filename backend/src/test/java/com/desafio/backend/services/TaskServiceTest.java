package com.desafio.backend.services;

import com.desafio.backend.entities.Task;
import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.repositories.TaskRepository;
import com.desafio.backend.services.exceptions.DatabaseException;
import com.desafio.backend.services.exceptions.ResourceNotFoundException;
import com.desafio.backend.services.exceptions.TooManyPendingTasksException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

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
        task = new Task(1L, "Curso Java", "Udemy", TaskStatus.PENDING);
        optionalTask = Optional.of(task);
    }

    @Test
    void whenFindAllTasksSortedByCreationDateReturnListOfTasks() {
        Mockito.when(repository.findAllByOrderByCreationDateAsc()).thenReturn(List.of(task));

        List<Task> response = service.findAllTasksSortedByCreationDate();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(task, response.get(0));
    }

    @Test
    void whenFindByIdThenReturnTaskInstance() {
        Mockito.when(repository.findById(1L)).thenReturn(optionalTask);

        Task response = service.findById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(task, response);
    }

    @Test
    void whenFindByIdThenThrowResourceNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(1L);
        });

        Assertions.assertEquals("Object not found.", exception.getMessage());
    }

    @Test
    void whenInsertThenReturnSuccess() {
        Mockito.when(repository.save(task)).thenReturn(task);

        Task response = service.insert(task);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(task, response);
    }

    @Test
    void whenInsertWithTooManyPendingTasksThenThrowTooManyPendingTasksException() {
        Mockito.when(repository.countByTaskStatus(TaskStatus.PENDING.getCode())).thenReturn(10L);

        TooManyPendingTasksException exception = Assertions.assertThrows(TooManyPendingTasksException.class, () -> {
            service.insert(task);
        });

        Assertions.assertEquals("Cannot create more than 10 pending tasks.", exception.getMessage());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        Mockito.doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteNonExistentTaskThenThrowResourceNotFoundException() {
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(1L);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(1L);
        });

        Assertions.assertEquals("Object not found.", exception.getMessage());
    }

    @Test
    void whenDeleteTaskWithConstraintViolationThenThrowDatabaseException() {
        Mockito.doThrow(new DataIntegrityViolationException("Data integrity violation")).when(repository).deleteById(1L);

        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(1L);
        });

        Assertions.assertEquals("Data integrity violation", exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Task updatedTask = new Task(1L, "Curso PHP", "Udemy", TaskStatus.CONCLUDED);

        Mockito.when(repository.getReferenceById(1L)).thenReturn(task);
        Mockito.when(repository.save(task)).thenReturn(updatedTask);

        Task response = service.update(1L, updatedTask);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(updatedTask, response);
    }
}
