package com.bestapp.todolist.constants;

public class Constants {
  // Select task categories in the form:
  public static final String[] CATEGORIES = new String[] {"Personal", "Work", "Things to Buy", "Meeting", "Recreation"};

  // Constant for return integer on Item Id check:
  public static final int ID_NOTFOUND = -1000;

  // Constant for submission and delete success/failure status before redirecting to Inventory page:
  public static final String ADD_SUCCESS_STATUS = "Add Success";
  public static final String UPDATE_SUCCESS_STATUS = "Update Success";
  public static final String FAILED_STATUS = "Failed";
  public static final String DELETE_SUCCESS = "Delete Success";
}
