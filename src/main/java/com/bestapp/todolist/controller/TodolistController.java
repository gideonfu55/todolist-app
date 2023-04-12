package com.bestapp.todolist.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.constants.Constants;

@Controller
public class TodolistController {

  List<TodoItem> items = new ArrayList<>();
  
  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) String id) {
    int itemIndex = getItemIndex(id);
    model.addAttribute("item", itemIndex == Constants.ID_NOTFOUND ? new TodoItem() : items.get(itemIndex));
    model.addAttribute("categories", Constants.CATEGORIES);
    return "taskform";
  }

  @GetMapping("/tasklist")
  public String getInventory(Model model) {
    model.addAttribute("items", items);
    return "tasklist";
  }

  @PostMapping("/submitItem")
  public String handleSubmit(TodoItem item, RedirectAttributes redirectAttributes) {
    int itemIndex = getItemIndex(item.getId());
    String status = Constants.ADD_SUCCESS_STATUS;
    if (itemIndex == Constants.ID_NOTFOUND && isNotPast(item.getDueDate())) {
      items.add(item);
      System.out.println("Item added: " + items.get(items.indexOf(item)));
    } 
    else if (isNotPast(item.getDueDate())) {
      items.set(itemIndex, item);
      status = Constants.UPDATE_SUCCESS_STATUS;
      System.out.println("Item updated: " + items.get(items.indexOf(item)));
    } else {
      status = Constants.FAILED_STATUS;
    }
    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/tasklist";
  }

  @DeleteMapping("/deleteItem")
  public void deleteItem(TodoItem item) {

  }

  public int getItemIndex(String id) {
    for (TodoItem item : items) {
      if (item.getId().equals(id)) return items.indexOf(item);
    }
    return Constants.ID_NOTFOUND;
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
