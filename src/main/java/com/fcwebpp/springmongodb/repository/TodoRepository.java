package com.fcwebpp.springmongodb.repository;

import com.fcwebpp.springmongodb.model.ToDodto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<ToDodto,String>{
    @Query("{'todo': ?0}")
    Optional<ToDodto> findByTodo(String todo);
}
