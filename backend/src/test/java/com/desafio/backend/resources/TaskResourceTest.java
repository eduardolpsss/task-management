package com.desafio.backend.resources;

import com.desafio.backend.entities.Task;
import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TaskResourceTest {

    @InjectMocks
    private TaskResource resource;

    @Mock
    private TaskService service;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task(1L, "Curso Java", "Udemy", TaskStatus.PENDING);
   }

    @Test
    void whenFindAllOrderByCreationDateAscReturnListOfTasks() {
        Mockito.when(service.findAllTasksSortedByCreationDate()).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = resource.findAllOrderByCreationDateAsc();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertTrue(List.class.isAssignableFrom(response.getBody().getClass()));
        Assertions.assertEquals(Task.class, response.getBody().get(0).getClass());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(task);

        ResponseEntity<Task> response = resource.findById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(Task.class, response.getBody().getClass());
        Assertions.assertEquals(1L, response.getBody().getId());
        Assertions.assertEquals("Curso Java", response.getBody().getTitle());
        Assertions.assertEquals("Udemy", response.getBody().getDescription());
        Assertions.assertEquals(TaskStatus.PENDING, response.getBody().getTaskStatus());
    }

    @Test
    void whenInsertThenReturnCreated() {
        Mockito.when(service.insert(Mockito.any())).thenReturn(task);

        ResponseEntity<Task> response = resource.insert(task);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(response.getBody().getTitle(), task.getTitle());
        Assertions.assertEquals(response.getBody().getDescription(), task.getDescription());
        Assertions.assertEquals(response.getBody().getTaskStatus(), task.getTaskStatus());
    }

    @Test
    void delete() {
        Mockito.doNothing().when(service).delete(Mockito.anyLong());

        ResponseEntity<Void> response = resource.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(service, Mockito.times(1)).delete(1L);
    }

    @Test
    void whenUpdateThenReturnSucess() {
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any())).thenReturn(task);

        ResponseEntity<Task> response = resource.update(1L, task);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody().getTitle(), task.getTitle());
        Assertions.assertEquals(response.getBody().getDescription(), task.getDescription());
        Assertions.assertEquals(response.getBody().getTaskStatus(), task.getTaskStatus());
    }
}