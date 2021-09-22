package com.BackEndHalf.BackEndPortfolio;

import java.util.Arrays;
import java.util.List;

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
  private Long id;
  private String firstName;
  private String lastName;
  private String title;
  private Long[] contacts;
  private int contactNum;
  private String quote;
  private String secret;
  private int lastTheme;
  private char symbol;
  private String symbolColor;
  private String cardColor;
  private String textColor;
  private String symbolBackgroundColor;
  
  public SiteUser() {
  }
  public SiteUser(String firstName, String lastName, String title, Long[] contacts, int contactNum,
      String quote, String secret, int lastTheme, char symbol, String symbolColor, String cardColor,
      String textColor, String symbolBackgroundColor) {
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
    this.symbolBackgroundColor = symbolBackgroundColor;
  }
  public Long getId() {
    return id;
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
  public Long[] getContacts() {
    return contacts;
  }
  public void setContacts(Long[] contacts) {
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
  public int getLastTheme() {
    return lastTheme;
  }
  public void setLastTheme(int lastTheme) {
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
  public String getSymbolBackgroundColor() {
    return symbolBackgroundColor;
  }
  public void setSymbolBackgroundColor(String symbolBackgroundColor) {
    this.symbolBackgroundColor = symbolBackgroundColor;
  }
  @Override
  public String toString() {
    return "SiteUser [cardColor=" + cardColor + ", contactNum=" + contactNum + ", contacts=" + Arrays.toString(contacts)
        + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", lastTheme=" + lastTheme + ", quote="
        + quote + ", secret=" + secret + ", symbol=" + symbol + ", symbolBackgroundColor=" + symbolBackgroundColor
        + ", symbolColor=" + symbolColor + ", textColor=" + textColor + ", title=" + title + "]";
  }
  

}
