package com.fcwebpp.springmongodb.controller;

import com.fcwebpp.springmongodb.exception.TodoCollectionException;
import com.fcwebpp.springmongodb.model.ToDodto;
import com.fcwebpp.springmongodb.repository.TodoRepository;
import com.fcwebpp.springmongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    TodoRepository todoRepo;
    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<ToDodto> todos = todoService.getAllTodos();
            return new ResponseEntity<List<ToDodto>>(todos,
                    todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodos(@RequestBody ToDodto todo){
        try{
            todoService.createTodo(todo);
            return  new ResponseEntity<ToDodto>(todo,HttpStatus.CREATED);
        }catch (ConstraintViolationException e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping("todos/{id}")
    public ResponseEntity<?> findSingleTodo(@PathVariable("id") String id){
       try {
           return new ResponseEntity<>(todoService.getSingleTodo(id),HttpStatus.OK);
       }catch (Exception e){
          return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
       }

    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id,@RequestBody ToDodto todo){
        try {
          todoService.updateTodo(id,todo);
          return new ResponseEntity<>("Update Todo with id"+id,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
      try{
          //todoRepo.deleteById(id);
          todoService.deleteById(id);
          return  new ResponseEntity("Successfully Deleted!: "+id,HttpStatus.OK);
    }catch (TodoCollectionException e){ //Exception
       return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
      }
    }
}
