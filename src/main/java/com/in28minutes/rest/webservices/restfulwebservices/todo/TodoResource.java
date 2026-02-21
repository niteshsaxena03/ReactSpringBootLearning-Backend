package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TodoResource {
    private TodoService todoService;

    public TodoResource(TodoService todoService){
        this.todoService=todoService;
    }
    
    @GetMapping("/users/{username}/todos")
    public List<Todo> retrieve(@PathVariable String username){
      return todoService.findByUsername(username);
    }
}
