package com.bestapp.todolist.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.constants.Constants;
import com.bestapp.todolist.repository.TodoItemRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoItemService {

  TodoItemRepository todoItemRepository;
  
  /*
   * ============
   * CRUD Methods
   * ============
   */

  // Create Or Update:
  public String saveOrUpdateItem(TodoItem item) {
    String status = Constants.FAILED_STATUS;
    if (!isNotPast(item.getDueDate())) {
      return status;
    }

    if (getItem(item.getId()) == null) {
      status = Constants.ADD_SUCCESS_STATUS;
    } else {
      status = Constants.UPDATE_SUCCESS_STATUS;
    }
    
    todoItemRepository.save(item);
    return status;
  }

  // Retrieve:
  public TodoItem getItem(Long id) {
    if (id == null) return null;
    Optional<TodoItem> item = todoItemRepository.findById(id);
    return unwrapItem(item, id);
  }

  public List<TodoItem> getItems() {
    return todoItemRepository.findAll();
  }
  
  // Delete:
  public void deleteItem(Long id) {
    todoItemRepository.deleteById(id);
  }

  // Mark Complete:
  public String markComplete(Long id) {
    TodoItem existingItem = getItem(id);
    String status = Constants.COMPLETION_SUCCESS;
    if (!existingItem.isCompleted()) {
      existingItem.setCompleted(true);
    } else {
      existingItem.setCompleted(false);
      status = Constants.COMPLETION_REMOVED;
    }

    todoItemRepository.save(existingItem);
    return status;
  }

  /*
   * ===============
   * Service Methods
   * ===============
   */
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
      return null;
    }
  }

  /**
   * Method name: isNotPast
   * 
   * @param date
   * @return
   * 
   * - Check if provided date is after the current date and return true if it is. Return false if not.
   * 
   */
  public boolean isNotPast(Date date) {
    // Convert Date to LocalDate
    LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Check if inputDate is not in the past and return true, false if otherwise.
    return !inputDate.isBefore(currentDate);
  }

}
