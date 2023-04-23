package com.bestapp.todolist.repository;

import com.bestapp.todolist.POJO.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
  
}
