package com.BackEndHalf.BackEndPortfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final WebSecurityConfig webSecurityConfig;
  public UserService(UserRepository userRepository, WebSecurityConfig webSecurityConfig) {
      this.userRepository = userRepository;
      this.webSecurityConfig = webSecurityConfig;
  }
  public getUser() {

  }
  public addContact() {
    
  }   //call updateUsers
  public updateUsers() {
    
  }
  public addUser() {
    
  }
  public deleteUser() {
    
  }
  public searchUser() {
    
  }
  public getTitles() {
    
  }
  public searchTitles() {
    
  }
  public attemptLogin_GetRole() {
    
  }
  public getUserByID() {
    
  }
  public getUsersSearch() {
    
  }
  public getTitlesSearch() {
    
  }

}
