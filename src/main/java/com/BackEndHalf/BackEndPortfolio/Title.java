package com.BackEndHalf.BackEndPortfolio;

import java.util.Comparator;
import java.util.List;

public class Title implements Comparator<Title> {
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
  
  public int compare(Title title1, Title title2) {
    return title1.getTitle().compareTo(title2.getTitle());
  }
  
  @Override
  public String toString() {
    return "Title [title=" + title + ", users=" + users + "]";
  }
  
}
