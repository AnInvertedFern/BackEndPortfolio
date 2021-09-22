package com.BackEndHalf.BackEndPortfolio;

public class GetRoleReply implements RESTReply{
  private String message;
  private boolean success;
  private boolean admin;
  private SiteUser currentUser;

  public GetRoleReply() {
  }
  public GetRoleReply(String message, boolean success, boolean admin, SiteUser currentUser) {
    this.message = message;
    this.success = success;
    this.admin = admin;
    this.currentUser = currentUser;
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
  public boolean isAdmin() {
    return admin;
  }
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
  public SiteUser getCurrentUser() {
    return currentUser;
  }
  public void setCurrentUser(SiteUser currentUser) {
    this.currentUser = currentUser;
  }
  @Override
  public String toString() {
    return "GetRoleReply [currentUser=" + currentUser + ", isAdmin=" + admin + ", message=" + message + ", success="
        + success + "]";
  }
  
}
