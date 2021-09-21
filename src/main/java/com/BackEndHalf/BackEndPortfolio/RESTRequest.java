package com.BackEndHalf.BackEndPortfolio;

public class RESTRequest {
  private String message;
  private SiteUser primaryUser;
  private SiteUser secondaryUser;
  
  public RESTRequest() {
  }
  public RESTRequest(String message, SiteUser primaryUser, SiteUser secondaryUser) {
    this.message = message;
    this.primaryUser = primaryUser;
    this.secondaryUser = secondaryUser;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public SiteUser getPrimaryUser() {
    return primaryUser;
  }
  public void setPrimaryUser(SiteUser primaryUser) {
    this.primaryUser = primaryUser;
  }
  public SiteUser getSecondaryUser() {
    return secondaryUser;
  }
  public void setSecondaryUser(SiteUser secondaryUser) {
    this.secondaryUser = secondaryUser;
  }

  @Override
  public String toString() {
    return "RESTRequest [message=" + message + ", primaryUser=" + primaryUser + ", secondaryUser=" + secondaryUser
        + "]";
  }

  
}
