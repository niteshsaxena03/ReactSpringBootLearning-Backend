package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TodoJpaResource {

    private TodoRepository todoRepository;

    public TodoJpaResource(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/users/{username}/todos")
    public List<Todo> retrieve(@PathVariable String username) {
        return todoRepository.findByUsername(username);
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo retrieveTodoById(@PathVariable String username, @PathVariable int id) {
        return todoRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String username, @PathVariable int id) {
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo) {
        todo.setUsername(username);
        todo.setId(0);
        Todo created = todoRepository.save(todo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/users/{username}/todos/{id}")
    public Todo updateTodo(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo) {
        todo.setUsername(username);
        return todoRepository.save(todo);
    }
}
