package com.bestapp.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bestapp.todolist.POJO.TodoItem;
import com.bestapp.todolist.constants.Constants;
import com.bestapp.todolist.service.TodoItemService;

import jakarta.validation.Valid;
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
  public String handleSubmit(@Valid TodoItem item, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      // redirectAttributes.addAttribute("item", item);
      redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
      return "redirect:/";
    }

    String status = todoItemService.saveOrUpdateItem(item);

    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

  @PostMapping("/markComplete")
  public String handleItemComplete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
    String status = todoItemService.markComplete(id);

    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:/todolist";
  }

  @PostMapping("/delete")
  public String deleteItem(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
    todoItemService.deleteItem(id);
    redirectAttributes.addFlashAttribute("status", Constants.DELETE_SUCCESS);
    return "redirect:/todolist";
  }

}
