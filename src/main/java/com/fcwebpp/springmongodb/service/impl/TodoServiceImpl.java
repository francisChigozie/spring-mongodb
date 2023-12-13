package com.fcwebpp.springmongodb.service.impl;

import com.fcwebpp.springmongodb.exception.TodoCollectionException;
import com.fcwebpp.springmongodb.model.ToDodto;
import com.fcwebpp.springmongodb.repository.TodoRepository;
import com.fcwebpp.springmongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(ToDodto todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<ToDodto> optionalToDodto = todoRepo.findByTodo(todo.getTodo());
        if (optionalToDodto.isPresent()){
           throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }else {
            todo.setCreateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }

    }

    @Override
    public List<ToDodto> getAllTodos() {
        List<ToDodto> todos = todoRepo.findAll();
        if (todos.size() > 0){
            return todos;
        }else {
            return new ArrayList<ToDodto>();
        }
    }

    @Override
    public ToDodto getSingleTodo(String id) throws TodoCollectionException {
        Optional<ToDodto> optionalTodo = todoRepo.findById(id);
        if (!optionalTodo.isPresent()){
            //return optionalTodo.get();
          throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
           // throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
           return optionalTodo.get();
        }
    }

    @Override
    public void updateTodo(String id, ToDodto todo) throws TodoCollectionException {
        Optional<ToDodto> todoWithId = todoRepo.findById(id);
        Optional<ToDodto> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
        if (todoWithId.isPresent()){

            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            ToDodto todoUpdate = todoWithId.get();
            todoUpdate.setCompleted(todo.getCompleted());
            todoUpdate.setDescription(todo.getDescription());
            //todoUpdate.setUpdatedAt(todo.getUpdatedAt());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoUpdate);

           /* ToDodto todoUpdate = todoWithId.get();
            todoUpdate.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoUpdate.getCompleted());
            todoUpdate.setTodo(todo.getTodo() != null ? todo.getTodo() : todoUpdate.getTodo());
            todoUpdate.setDescription(todo.getDescription() != null ? todo.getDescription() : todoUpdate.getDescription());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoUpdate);*/
            //return new ResponseEntity<>(todoUpdate, HttpStatus.ACCEPTED);*/
        }else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteById(String id) throws TodoCollectionException {
        Optional<ToDodto> optionalTodo = todoRepo.findById(id);
        if (!optionalTodo.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
            todoRepo.deleteById(id);
        }
    }
}
