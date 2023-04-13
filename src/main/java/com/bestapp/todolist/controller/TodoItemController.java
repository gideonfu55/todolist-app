package com.bestapp.todolist.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.constants.Constants;
import com.bestapp.todolist.repository.TodoItemRepository;

@Controller
public class TodoItemController {

  @Autowired
  private TodoItemRepository todoItemRepository;
  
  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) String id) {

    model.addAttribute("item", checkTodoItemPresent(id) == Constants.ID_NOTFOUND ? new TodoItem() : todoItemRepository.findById(Long.valueOf(id)));
    model.addAttribute("categories", Constants.CATEGORIES);

    return "todoform";
  }

  @GetMapping("/todolist")
  public String getList(Model model) {

    List<TodoItem> items = todoItemRepository.findAll();
    model.addAttribute("items", items);

    return "todolist";
  }

  @PostMapping("/submitItem")
  public String handleSubmit(TodoItem item, RedirectAttributes redirectAttributes) {

    String status = Constants.ADD_SUCCESS_STATUS;

    if (checkTodoItemPresent(item.getId()) == Constants.ID_NOTFOUND && isNotPast(item.getDueDate())) {
      todoItemRepository.save(item);
      // System.out.println("Item added: " + todoItemRepository.findById(Long.valueOf(item.getId())));
    } else if (isNotPast(item.getDueDate())) {
      todoItemRepository.save(item);
      status = Constants.UPDATE_SUCCESS_STATUS;
      // System.out.println("Item updated: " + todoItemRepository.findById(Long.valueOf(item.getId())));
    } else {
      status = Constants.FAILED_STATUS;
    }

    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

  @PostMapping("/delete")
  public String deleteItem(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
    String status = Constants.DELETE_SUCCESS;
    todoItemRepository.deleteById(Long.valueOf(id));
    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

  /**
   * Method name: checkTodoItemPresent
   * 
   * @param id
   * @return
   * 
   * - Checks if TodoItem is present in Repository based on id provided and return either true or false.
   * 
   */
  public boolean checkTodoItemPresent(String id) {
    Optional<TodoItem> existingItem = todoItemRepository.findById(Long.valueOf(id));
    boolean itemIsPresent = existingItem.isPresent() ? true : Constants.ID_NOTFOUND;
    return itemIsPresent;
  }

  public boolean isNotPast(Date date) {
    // Convert Date to LocalDate
    LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Check if inputDate is on or after currentDate
    return !inputDate.isBefore(currentDate);
  }

}
