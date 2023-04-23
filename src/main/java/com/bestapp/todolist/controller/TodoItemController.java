package com.bestapp.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.constants.Constants;
import com.bestapp.todolist.service.TodoItemService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class TodoItemController {

  TodoItemService todoItemService;
  
  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) Long id) {

    model.addAttribute("item", todoItemService.getItem(id) == null ? new TodoItem() : todoItemService.getItem(id));
    model.addAttribute("categories", Constants.CATEGORIES);

    return "todoform";
  }

  @GetMapping("/todolist")
  public String getList(Model model) {

    List<TodoItem> items = todoItemService.getItems();
    model.addAttribute("items", items);

    return "todolist";
  }

  @PostMapping("/submitItem")
  public String handleSubmit(TodoItem item, RedirectAttributes redirectAttributes) {

    String status = Constants.ADD_SUCCESS_STATUS;

    if ( todoItemService.getItem(item.getId()) == null && todoItemService.isNotPast(item.getDueDate()) ) {
      todoItemService.saveItem(item);
      System.out.println("Item added: " + todoItemService.getItem(item.getId()));
    } else if ( todoItemService.getItem(item.getId()) != null && todoItemService.isNotPast(item.getDueDate()) ) {
      todoItemService.saveItem(item);
      status = Constants.UPDATE_SUCCESS_STATUS;
      System.out.println("Item updated: " + todoItemService.getItem(item.getId()));
    } else {
      status = Constants.FAILED_STATUS;
    }

    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

  @PostMapping("/markComplete")
  public String handleItemComplete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
    TodoItem existingItem = todoItemService.getItem(id);
    if (existingItem != null && !existingItem.isCompleted()) {
      existingItem.setCompleted(true);
      todoItemService.saveItem(existingItem);
      redirectAttributes.addFlashAttribute("status", Constants.COMPLETION_SUCCESS);
    } else if (existingItem != null) {
      existingItem.setCompleted(false);
      todoItemService.saveItem(existingItem);
      redirectAttributes.addFlashAttribute("status", Constants.COMPLETION_REMOVED);
    } else {
      redirectAttributes.addFlashAttribute("status", Constants.FAILED_STATUS);
    }

    return "redirect:/todolist";
  }

  @PostMapping("/delete")
  public String deleteItem(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
    String status = Constants.DELETE_SUCCESS;
    todoItemService.deleteItem(id);
    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

}
