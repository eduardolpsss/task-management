package com.desafio.backend.services;

import com.desafio.backend.entities.Task;
import com.desafio.backend.entities.enums.TaskStatus;
import com.desafio.backend.repositories.TaskRepository;
import com.desafio.backend.services.exceptions.DatabaseException;
import com.desafio.backend.services.exceptions.ResourceNotFoundException;
import com.desafio.backend.services.exceptions.TooManyPendingTasksException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> findAllTasksSortedByCreationDate() {
        return repository.findAllByOrderByCreationDateAsc();
    }

    public Task findById(Long id) {
        Optional<Task> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Task insert(Task obj) {
        long pendingTasksCount = repository.countByTaskStatus(TaskStatus.PENDING.getCode());
        if (pendingTasksCount >= 10) {
            throw new TooManyPendingTasksException("Cannot create more than 10 pending tasks.");
        }
        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Task update(Long id, Task obj) {
        try {
            Task entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Task entity, Task obj) {
        entity.setTitle(obj.getTitle());
        entity.setDescription(obj.getDescription());
        entity.setTaskStatus(obj.getTaskStatus());
    }
}
