package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

public class UserReply implements RESTReply{
  private String message;
  private boolean success;
  private List<SiteUser> users;
  private List<SiteUser> allUsers;

  public UserReply() {
  }
  public UserReply(String message, boolean success, List<SiteUser> users, List<SiteUser> allUsers) {
    this.message = message;
    this.success = success;
    this.users = users;
    this.allUsers = allUsers;
  }
  
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public boolean isSuccess() {
    return success;
  }
  public void setSuccess(boolean success) {
    this.success = success;
  }
  public List<SiteUser> getUsers() {
    return users;
  }
  public void setUsers(List<SiteUser> users) {
    this.users = users;
  }
  public List<SiteUser> getAllUsers() {
    return allUsers;
  }
  public void setAllUsers(List<SiteUser> allUsers) {
    this.allUsers = allUsers;
  }
  @Override
  public String toString() {
    return "UserReply [allUsers=" + allUsers + ", message=" + message + ", success=" + success + ", users=" + users
        + "]";
  }

  
  
}
