package com.will.todo_backend.repository;

import com.will.todo_backend.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo extends JpaRepository<TodoEntity, Long> {
}
