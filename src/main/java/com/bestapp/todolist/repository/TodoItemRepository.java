package com.bestapp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bestapp.todolist.POJO.TodoItem;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
  
}
