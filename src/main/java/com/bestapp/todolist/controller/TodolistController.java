package com.bestapp.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodolistController {
  
  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) String id) {
    // int itemIndex = getItemIndex(id);
    return "taskform";
  }

}
