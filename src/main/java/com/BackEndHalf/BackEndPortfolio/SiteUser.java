package com.BackEndHalf.BackEndPortfolio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SiteUser {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String firstName;
  private String lastName;
  private String title;
  private SiteUser[] contacts;
  private int contactNum;
  private String quote;
  private String secret;
  private String lastTheme;
  private char symbol;
  private String symbolColor;
  private String cardColor;
  private String textColor;
  
  public SiteUser() {
  }
  public SiteUser(int id, String firstName, String lastName, String title, SiteUser[] contacts, int contactNum,
      String quote, String secret, String lastTheme, char symbol, String symbolColor, String cardColor,
      String textColor) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.title = title;
    this.contacts = contacts;
    this.contactNum = contactNum;
    this.quote = quote;
    this.secret = secret;
    this.lastTheme = lastTheme;
    this.symbol = symbol;
    this.symbolColor = symbolColor;
    this.cardColor = cardColor;
    this.textColor = textColor;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public SiteUser[] getContacts() {
    return contacts;
  }
  public void setContacts(SiteUser[] contacts) {
    this.contacts = contacts;
  }
  public int getContactNum() {
    return contactNum;
  }
  public void setContactNum(int contactNum) {
    this.contactNum = contactNum;
  }
  public String getQuote() {
    return quote;
  }
  public void setQuote(String quote) {
    this.quote = quote;
  }
  public String getSecret() {
    return secret;
  }
  public void setSecret(String secret) {
    this.secret = secret;
  }
  public String getLastTheme() {
    return lastTheme;
  }
  public void setLastTheme(String lastTheme) {
    this.lastTheme = lastTheme;
  }
  public char getSymbol() {
    return symbol;
  }
  public void setSymbol(char symbol) {
    this.symbol = symbol;
  }
  public String getSymbolColor() {
    return symbolColor;
  }
  public void setSymbolColor(String symbolColor) {
    this.symbolColor = symbolColor;
  }
  public String getCardColor() {
    return cardColor;
  }
  public void setCardColor(String cardColor) {
    this.cardColor = cardColor;
  }
  public String getTextColor() {
    return textColor;
  }
  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }

}
