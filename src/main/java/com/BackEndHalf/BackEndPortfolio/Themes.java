package com.BackEndHalf.BackEndPortfolio;

public class Themes {
  private String inactiveTabColor;
  private String activeTabColor;
  private String toolbarColor;
  private String searchBarColor;
  private String logoutButtonColor;
  private String backgroundColor;
  private String textColor;
  private String addUserColor;
  private String editUserColor;
  private String confirmThemeColor;
  public Themes() {
  }
  public Themes(String inactiveTabColor, String activeTabColor, String toolbarColor, String searchBarColor,
      String logoutButtonColor, String backgroundColor, String textColor, String addUserColor, String editUserColor,
      String confirmThemeColor) {
    this.inactiveTabColor = inactiveTabColor;
    this.activeTabColor = activeTabColor;
    this.toolbarColor = toolbarColor;
    this.searchBarColor = searchBarColor;
    this.logoutButtonColor = logoutButtonColor;
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
    this.addUserColor = addUserColor;
    this.editUserColor = editUserColor;
    this.confirmThemeColor = confirmThemeColor;
  }
  public String getInactiveTabColor() {
    return inactiveTabColor;
  }
  public void setInactiveTabColor(String inactiveTabColor) {
    this.inactiveTabColor = inactiveTabColor;
  }
  public String getActiveTabColor() {
    return activeTabColor;
  }
  public void setActiveTabColor(String activeTabColor) {
    this.activeTabColor = activeTabColor;
  }
  public String getToolbarColor() {
    return toolbarColor;
  }
  public void setToolbarColor(String toolbarColor) {
    this.toolbarColor = toolbarColor;
  }
  public String getSearchBarColor() {
    return searchBarColor;
  }
  public void setSearchBarColor(String searchBarColor) {
    this.searchBarColor = searchBarColor;
  }
  public String getLogoutButtonColor() {
    return logoutButtonColor;
  }
  public void setLogoutButtonColor(String logoutButtonColor) {
    this.logoutButtonColor = logoutButtonColor;
  }
  public String getBackgroundColor() {
    return backgroundColor;
  }
  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }
  public String getTextColor() {
    return textColor;
  }
  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }
  public String getAddUserColor() {
    return addUserColor;
  }
  public void setAddUserColor(String addUserColor) {
    this.addUserColor = addUserColor;
  }
  public String getEditUserColor() {
    return editUserColor;
  }
  public void setEditUserColor(String editUserColor) {
    this.editUserColor = editUserColor;
  }
  public String getConfirmThemeColor() {
    return confirmThemeColor;
  }
  public void setConfirmThemeColor(String confirmThemeColor) {
    this.confirmThemeColor = confirmThemeColor;
  }
  
}
