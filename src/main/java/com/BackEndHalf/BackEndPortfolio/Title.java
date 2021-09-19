package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

public class Title {
  private String title;
  private List<SiteUser> users;
  public Title() {
  }
  public Title(String title, List<SiteUser> users) {
    this.title = title;
    this.users = users;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public List<SiteUser> getUsers() {
    return users;
  }
  public void setUsers(List<SiteUser> users) {
    this.users = users;
  }
  
}
