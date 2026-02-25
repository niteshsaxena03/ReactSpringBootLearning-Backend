package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:5173")
//@RestController
public class TodoResource {
    private TodoService todoService;

    public TodoResource(TodoService todoService){
        this.todoService=todoService;
    }
    
    @GetMapping("/users/{username}/todos")
    public List<Todo> retrieve(@PathVariable String username){
      return todoService.findByUsername(username);
    }
    @GetMapping("/users/{username}/todos/{id}")
    public Todo retrieveTodoById(@PathVariable String username,@PathVariable int id){
      return todoService.findById(id);
    }
    @DeleteMapping("/users/{username}/todos/{id}")
    public void deleteTodoById(@PathVariable String username,@PathVariable int id){
      todoService.deleteById(id);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo){
      Todo created = todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
      return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri()
      ).body(created);
    }

    @PutMapping("/users/{username}/todos/{id}")
    public Todo updateTodo(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo){
      todoService.updateTodo(todo);
      return todo;
    }
}
