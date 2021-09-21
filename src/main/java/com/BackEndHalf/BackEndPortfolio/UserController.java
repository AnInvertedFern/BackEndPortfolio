package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<UserReply> getUsers()  {
    List<SiteUser> tempUsers = this.userService.getUsers();
    for (SiteUser tempUser : tempUsers) {System.out.println( tempUser.toString() ); }
    UserReply response = new UserReply();
    response.setAllUsers(tempUsers);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/addcontact/")
  public ResponseEntity<SiteUser> addContact (@RequestBody SiteUser userAddTo, @RequestBody SiteUser userToAdd) {
    SiteUser tempUsers = this.userService.addContact(userAddTo, userToAdd);
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/")
  public ResponseEntity<UserReply>  updateUser (@RequestBody SiteUser user) {
    SiteUser tempUsers = userService.updateUsers(user);
    UserReply response = new UserReply();
    response.setUsers( Arrays.asList(tempUsers) );
    return new ResponseEntity<>(response, HttpStatus.OK);

  }
  @PutMapping("/api/users/")
  public ResponseEntity<UserReply> addUser (@RequestBody RESTRequest request) {
    SiteUser tempUsers = userService.addUser(request.getPrimaryUser());
    UserReply response = new UserReply();
    response.setUsers( Arrays.asList(tempUsers) );
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @DeleteMapping("/api/users/{id}")
  public ResponseEntity<UserReply> deleteUser (@PathVariable(value="id") Long userID) {
    String stringResponse = userService.deleteUser(userID);
    UserReply response = new UserReply();
    response.setMessage( Long.toString(userID) );
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }

  // @PostMapping("/api/users/search/")
  // public searchUser()  {
    
  // }
  @GetMapping("/api/titles/all/")
  public ResponseEntity<TitlesReply> getTitles()  {
    List<Title> tempTitles = this.userService.getTitles();
    for (Title tempTitle : tempTitles) {System.out.println( tempTitle.toString() ); }
    TitlesReply response = new TitlesReply();
    response.setAllTitles(tempTitles);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
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
  public ResponseEntity<UserReply> getUsersSearch (@RequestParam(value="searchValue") String query) {
    for (int i =0; i<10;i++){System.out.println(query);}
    List<SiteUser> tempUsers = this.userService.getUsersSearch(query);
    UserReply response = new UserReply();
    response.setUsers(tempUsers);
    // response.setAllUsers(userService.getUsers()); 
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @PostMapping("/api/titles/search/")
  public ResponseEntity<TitlesReply> getTitlesSearch (@RequestParam(value="searchValue")  String query) {
    for (int i =0; i<10;i++){System.out.println(query);}
    List<Title> tempTitles = this.userService.getTitlesSearch(query);
    TitlesReply response = new TitlesReply();
    response.setTitles(tempTitles);
    response.setAllTitles(userService.getTitles()); 
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }

}
