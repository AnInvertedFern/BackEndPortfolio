package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

public class TitlesReply implements RESTReply{
  private String message;
  private boolean success;
  private List<Title> titles;
  private List<Title> allTitles;

  public TitlesReply() {
  }
  public TitlesReply(String message, boolean success, List<Title> titles, List<Title> allTitles) {
    this.message = message;
    this.success = success;
    this.titles = titles;
    this.allTitles = allTitles;
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
  public List<Title> getTitles() {
    return titles;
  }
  public void setTitles(List<Title> titles) {
    this.titles = titles;
  }
  public List<Title> getAllTitles() {
    return allTitles;
  }
  public void setAllTitles(List<Title> allTitles) {
    this.allTitles = allTitles;
  }
  @Override
  public String toString() {
    return "TitlesReply [allTitles=" + allTitles + ", message=" + message + ", success=" + success + ", titles="
        + titles + "]";
  }

}
