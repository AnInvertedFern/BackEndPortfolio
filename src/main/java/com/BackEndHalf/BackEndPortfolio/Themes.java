package com.BackEndHalf.BackEndPortfolio;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Themes implements Comparator<Themes> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
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
    
  private String refreshUserColor;
  private String popupColor;
  private String titleShadowColor;
  private String searchTitleShadowColor;
  private String footerSeperatorColor;
  private String loginShadowColor;
  private String inputColor;
  private String inputButtonColor;

  public Themes() {
  }
  public Themes(String inactiveTabColor, String activeTabColor, String toolbarColor, String searchBarColor,
      String logoutButtonColor, String backgroundColor, String textColor, String addUserColor, String editUserColor,
      String confirmThemeColor, String refreshUserColor, String popupColor, String titleShadowColor,
      String searchTitleShadowColor, String footerSeperatorColor, String loginShadowColor, String inputColor,
      String inputButtonColor) {
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
    this.refreshUserColor = refreshUserColor;
    this.popupColor = popupColor;
    this.titleShadowColor = titleShadowColor;
    this.searchTitleShadowColor = searchTitleShadowColor;
    this.footerSeperatorColor = footerSeperatorColor;
    this.loginShadowColor = loginShadowColor;
    this.inputColor = inputColor;
    this.inputButtonColor = inputButtonColor;
  }
  public Long getId() {
    return id;
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
  public String getRefreshUserColor() {
    return refreshUserColor;
  }
  public void setRefreshUserColor(String refreshUserColor) {
    this.refreshUserColor = refreshUserColor;
  }
  public String getPopupColor() {
    return popupColor;
  }
  public void setPopupColor(String popupColor) {
    this.popupColor = popupColor;
  }
  public String getTitleShadowColor() {
    return titleShadowColor;
  }
  public void setTitleShadowColor(String titleShadowColor) {
    this.titleShadowColor = titleShadowColor;
  }
  public String getSearchTitleShadowColor() {
    return searchTitleShadowColor;
  }
  public void setSearchTitleShadowColor(String searchTitleShadowColor) {
    this.searchTitleShadowColor = searchTitleShadowColor;
  }
  public String getFooterSeperatorColor() {
    return footerSeperatorColor;
  }
  public void setFooterSeperatorColor(String footerSeperatorColor) {
    this.footerSeperatorColor = footerSeperatorColor;
  }
  public String getLoginShadowColor() {
    return loginShadowColor;
  }
  public void setLoginShadowColor(String loginShadowColor) {
    this.loginShadowColor = loginShadowColor;
  }
  public String getInputColor() {
    return inputColor;
  }
  public void setInputColor(String inputColor) {
    this.inputColor = inputColor;
  }
  public String getInputButtonColor() {
    return inputButtonColor;
  }
  public void setInputButtonColor(String inputButtonColor) {
    this.inputButtonColor = inputButtonColor;
  }
  public int compare(Themes theme1, Themes theme2) {
    return theme1.getId().compareTo(theme2.getId());
  }

  @Override
  public String toString() {
    return "Themes [activeTabColor=" + activeTabColor + ", addUserColor=" + addUserColor + ", backgroundColor="
        + backgroundColor + ", confirmThemeColor=" + confirmThemeColor + ", editUserColor=" + editUserColor
        + ", footerSeperatorColor=" + footerSeperatorColor + ", id=" + id + ", inactiveTabColor=" + inactiveTabColor
        + ", inputButtonColor=" + inputButtonColor + ", inputColor=" + inputColor + ", loginShadowColor="
        + loginShadowColor + ", logoutButtonColor=" + logoutButtonColor + ", popupColor=" + popupColor
        + ", refreshUserColor=" + refreshUserColor + ", searchBarColor=" + searchBarColor + ", searchTitleShadowColor="
        + searchTitleShadowColor + ", textColor=" + textColor + ", titleShadowColor=" + titleShadowColor
        + ", toolbarColor=" + toolbarColor + "]";
  }

  
}
