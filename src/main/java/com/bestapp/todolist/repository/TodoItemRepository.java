package com.bestapp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestapp.todolist.POJO.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
  
}
