package com.bestapp.todolist.POJO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

public class TodoItem {

  private String id;
  private String category;
  private String title;
  private String description;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dueDate;

  public TodoItem() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String formatDate(Date date) {
    String formattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(date);
    return formattedDate;
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", category='" + getCategory() + "'" +
      ", title='" + getTitle() + "'" +
      ", description='" + getDescription() + "'" +
      ", dueDate='" + getDueDate() + "'" +
      "}";
  }

}
