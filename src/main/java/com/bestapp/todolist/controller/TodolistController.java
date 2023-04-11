package com.bestapp.todolist.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  @PostMapping("/submitItem")
  public String handleSubmit(TodoItem item, RedirectAttributes redirectAttributes) {
    int itemIndex = getItemIndex(item.getId());
    String status = Constants.ADD_SUCCESS_STATUS;
    if (itemIndex == Constants.ID_NOTFOUND) {
      items.add(item);
      System.out.println("Item added: " + items.get(items.indexOf(item)));
    } else if (within5Days(item.getDueDate(), items.get(itemIndex).getDueDate())) {
      items.set(itemIndex, item);
      status = Constants.UPDATE_SUCCESS_STATUS;
      System.out.println("Item updated: " + items.get(items.indexOf(item)));
    } else {
      status = Constants.FAILED_STATUS;
      System.out.println(status);
    }
    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/inventory";
  }

  private boolean within5Days(Date dateOfCompletion, Date dateOfCompletion2) {
    return false;
  }

  public int getItemIndex(String id) {
    for (TodoItem item : items) {
      if (item.getId().equals(id)) return items.indexOf(item);
    }
    return Constants.ID_NOTFOUND;
  }

}
