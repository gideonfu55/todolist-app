package com.bestapp.todolist.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.exception.ItemNotFoundException;
import com.bestapp.todolist.repository.TodoItemRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoItemService {

  TodoItemRepository todoItemRepository;
  
  // CRUD Methods:
  public TodoItem getItem(Long id) {
    if (id == null) {
      return null;
    }
    Optional<TodoItem> item = todoItemRepository.findById(id);
    return unwrapItem(item, id);
  }

    public void saveItem(TodoItem item) {
    if (!isNotPast(item.getDueDate())) {
      return;
    }
    todoItemRepository.save(item);
  }

  public void deleteItem(Long id) {
    todoItemRepository.deleteById(id);
  }

  public List<TodoItem> getItems() {
    return todoItemRepository.findAll();
  }

  /**
   * Method name: unwrapItem
   * 
   * @param entity
   * @param id
   * @return
   * 
   * - Return a TodoItem from an Optional TodoItem after checking if item is present. Throw ItemNotFoundException is otherwise.
   * 
   */
  public TodoItem unwrapItem(Optional<TodoItem> entity, Long id) {
    if (entity.isPresent())
      return entity.get();
    else {
      throw new ItemNotFoundException(id);
    }
  }

  /**
   * Method name: isNotPast
   * 
   * @param date
   * @return
   * 
   * - Check if provided date is before the current date. Return false if not.
   * 
   */
  public boolean isNotPast(Date date) {
    // Convert Date to LocalDate
    LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Check if inputDate is on or after currentDate
    return !inputDate.isBefore(currentDate);
  }

}
