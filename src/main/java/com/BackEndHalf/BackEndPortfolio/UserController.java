package com.BackEndHalf.BackEndPortfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {
  @Autowired
  private final UserService userService;
  UserController(UserService userService) { 
      this.userService = userService;
  }
  @GetMapping("/api/users/all")
  public getUsers()  {
    
  }
  @PostMapping("/api/users/addcontact/")
  public addContact () {
    
  }
  @PostMapping("/api/users/")
  public updateUsers () {
    
  }
  @PutMapping("/api/users/")
  public addUser () {
    
  }
  @DeleteMapping("/api/users/{id}")
  public deleteUser () {
    
  }

  @GetMapping("/api/users/search/")
  public searchUser()  {
    
  }
  @GetMapping("/api/titles/all/")
  public getTitles()  {
    
  }
  @GetMapping("/api/titles/search/")
  public searchTitles () {
    
  }
  @GetMapping("/login/get_role/")
  public attemptLogin_GetRole () {
    
  }
  @GetMapping("/api/users/{ID}/")
  public getUserByID () {
    
  }
  @GetMapping("/api/users/search/")
  public getUsersSearch () {
    
  }
  @GetMapping("/api/titles/search/")
  public getTitlesSearch () {
    
  }

}
