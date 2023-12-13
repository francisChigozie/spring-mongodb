package com.fcwebpp.springmongodb.service;

import com.fcwebpp.springmongodb.exception.TodoCollectionException;
import com.fcwebpp.springmongodb.model.ToDodto;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface TodoService {

    public void createTodo(ToDodto todo) throws ConstraintViolationException, TodoCollectionException;

    public List<ToDodto> getAllTodos();

    public ToDodto getSingleTodo(String id) throws TodoCollectionException;

    public void updateTodo(String id, ToDodto todo) throws TodoCollectionException;

    public void deleteById(String id) throws TodoCollectionException;
}
