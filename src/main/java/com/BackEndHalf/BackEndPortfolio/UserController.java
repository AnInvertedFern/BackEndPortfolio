package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<List<SiteUser>> getUsers()  {
    List<SiteUser> tempUsers = this.userService.getUsers();
    for (SiteUser tempUser : tempUsers) {System.out.println( tempUser.toString() ); }
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/addcontact/")
  public ResponseEntity<SiteUser> addContact (@RequestBody SiteUser userAddTo, @RequestBody SiteUser userToAdd) {
    SiteUser tempUsers = this.userService.addContact(userAddTo, userToAdd);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/")
  public ResponseEntity<SiteUser>  updateUser (@RequestBody SiteUser user) {
    SiteUser tempUsers = userService.updateUsers(user);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);

  }
  @PutMapping("/api/users/")
  public ResponseEntity<SiteUser> addUser (@RequestBody SiteUser user) {
    SiteUser tempUsers = userService.addUser(user);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @DeleteMapping("/api/users/{id}")
  public ResponseEntity<String> deleteUser (@PathVariable Long userID) {
    String stringResponse = userService.deleteUser(userID);
    return new ResponseEntity<>(stringResponse, HttpStatus.OK);
    
  }

  // @PostMapping("/api/users/search/")
  // public searchUser()  {
    
  // }
  @GetMapping("/api/titles/all/")
  public ResponseEntity<List<Title>> getTitles()  {
    List<Title> tempTitles = this.userService.getTitles();
    for (Title tempTitle : tempTitles) {System.out.println( tempTitle.toString() ); }
    return new ResponseEntity<>(tempTitles, HttpStatus.OK);
    
  }
  // @PostMapping("/api/titles/search/")
  // public searchTitles () {
    
  // }
  @GetMapping("/login/get_role/")
  public ResponseEntity<SiteUser> attemptLogin_GetRole ( Principal user ) {
    SiteUser tempUsers = userService.attemptLogin_GetRole(user);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
  }
  @GetMapping("/api/users/{ID}/")
  public ResponseEntity<SiteUser> getUserByID (@PathVariable Long userID) {
    SiteUser tempUsers = userService.getUserByID(userID);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
  }
  @PostMapping("/api/users/search/")
  public ResponseEntity<List<SiteUser>> getUsersSearch (@RequestBody String query) {
    List<SiteUser> tempUsers = this.userService.getUsersSearch(query);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @PostMapping("/api/titles/search/")
  public ResponseEntity<List<Title>> getTitlesSearch (@RequestBody String query) {
    List<Title> tempTitles = this.userService.getTitlesSearch(query);
    return new ResponseEntity<>(tempTitles, HttpStatus.OK);
    
  }

}
