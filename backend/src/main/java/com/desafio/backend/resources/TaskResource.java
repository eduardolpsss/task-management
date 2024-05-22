package com.desafio.backend.resources;

import com.desafio.backend.entities.Task;
import com.desafio.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskResource {

    public static final String ID = "/{id}";

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> findAllOrderByCreationDateAsc() {
        List<Task> list = service.findAllTasksSortedByCreationDate();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = ID)
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Task> insert(@RequestBody Task obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
