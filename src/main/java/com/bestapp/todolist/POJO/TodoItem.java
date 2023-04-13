package com.bestapp.todolist.POJO;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "todo_items")
public class TodoItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "category")
  private String category;

  @Column(name = "title", nullable = false)
  @NotNull
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "due_date", nullable = false)
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dueDate;

  @Column(name = "completed", nullable = false)
  @NotNull
  private boolean completed;

}
