package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
  @Autowired
  private final WebSecurityConfig webSecurityConfig;
  UserController(UserService userService, WebSecurityConfig webSecurityConfig) { 
      this.userService = userService;
      this.webSecurityConfig = webSecurityConfig;
  }
  @GetMapping("/api/users/all")
  public ResponseEntity<UserReply> getUsers(Principal userLoggedIn)  {
    System.out.println("Received A getUsers Request");

    boolean success = true;
    List<SiteUser> tempUsers = null;
    try{
      tempUsers = this.userService.getUsers(userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }
    
    UserReply response = new UserReply();
    
    response.setAllUsers(tempUsers);
    response.setSuccess(success);
    
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/addcontact/")
  public ResponseEntity<UserReply> addContact (@RequestBody RESTRequest request, Principal userLoggedIn) {
    System.out.println("Received A addContact Request");
    boolean success = true;
    SiteUser tempUsers = null;
    try{
      tempUsers = this.userService.addContact(request.getPrimaryUser(), request.getSecondaryUser(), userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }
    UserReply response = new UserReply();
    response.setUsers( Arrays.asList(tempUsers) );
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @PostMapping("/api/users/")
  public ResponseEntity<UserReply>  updateUser (@RequestBody SiteUser user, Principal userLoggedIn) {
    System.out.println("Received A updateUser Request");
    boolean success = true;
    SiteUser tempUsers = null;
    try{
      tempUsers = userService.updateUsers(user, userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }

    UserReply response = new UserReply();
    response.setUsers( Arrays.asList(tempUsers) );
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }
  @PutMapping("/api/users/")
  public ResponseEntity<UserReply> addUser (@RequestBody RESTRequest request) throws Exception {
    System.out.println("Received A addUser Request");
    boolean success = true;
    SiteUser tempUsers = null;
    try{
      tempUsers = userService.addUser(request.getPrimaryUser());
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }

    if (tempUsers != null && success) {
      try {

        webSecurityConfig.getUserDetails().createUser(User.withDefaultPasswordEncoder()
        .username( Long.toString( tempUsers.getId()) )
        .password(request.getMessage())
        .roles("USER")
        .build() );

      } catch (Exception e) {

        success = false;
        try {
          userService.removeUserOnFail(request.getPrimaryUser().getId());
        } catch (Exception f) {
          throw new Exception();
        }

      }
    }
    UserReply response = new UserReply();
    response.setUsers( Arrays.asList(tempUsers) );
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @DeleteMapping("/api/users/{id}")
  public ResponseEntity<UserReply> deleteUser (@PathVariable(value="id") Long userID, Principal userLoggedIn) {
    System.out.println("Received A deleteUser Request");
    boolean success = true;
    String stringResponse = null;
    try{
      stringResponse = userService.deleteUser(userID, userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (stringResponse == null) { success = false; }
    }
    UserReply response = new UserReply();
    response.setMessage( Long.toString(userID) );
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @GetMapping("/api/titles/all/")
  public ResponseEntity<TitlesReply> getTitles()  {
    System.out.println("Received A getTitles Request");
    boolean success = true;
    List<Title> tempTitles = null;
    try{
      tempTitles = this.userService.getTitles();
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempTitles == null) { success = false; }
    }
    TitlesReply response = new TitlesReply();
    response.setAllTitles(tempTitles);
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @GetMapping("/login/get_role/")
  public ResponseEntity<GetRoleReply> attemptLogin_GetRole ( Principal userLoggedIn ) {
    System.out.println("Received A attemptLogin_GetRole Request");
    boolean success = true;
    SiteUser tempUsers = null;
    try{
      tempUsers = userService.attemptLogin_GetRole(userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }
    boolean isAdmin = false;
    Collection<GrantedAuthority> roles = null;
    if (success) {
      roles = ((UsernamePasswordAuthenticationToken) userLoggedIn ).getAuthorities();
      for (GrantedAuthority authority : roles) {
        if (authority.getAuthority().equals("ROLE_ADMIN")) {
          isAdmin = true;
        }
      }
    }
    GetRoleReply response = new GetRoleReply("", success, isAdmin, tempUsers);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  @GetMapping("/logout/")
  public ResponseEntity<String> attemptLogout (  ) {
    System.out.println("Received A attemptLogout Request");
    return new ResponseEntity<>("[\"Done\"]", HttpStatus.OK);
  }
  @GetMapping("/logout/give_response/")
  public ResponseEntity<String> attemptLogout2 (  ) {
    System.out.println("Received A attemptLogout2 Request");
    return new ResponseEntity<>("[\"Done\"]", HttpStatus.OK);
  }
  @PostMapping("/api/users/search/")
  public ResponseEntity<UserReply> getUsersSearch (@RequestParam(value="searchValue") String query, Principal userLoggedIn) {
    System.out.println("Received A getUsersSearch Request");
    boolean success = true;
    List<SiteUser> tempUsers = null;
    try{
      tempUsers = this.userService.getUsersSearch(query, userLoggedIn);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempUsers == null) { success = false; }
    }
    UserReply response = new UserReply();
    response.setUsers(tempUsers);
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }
  @PostMapping("/api/titles/search/")
  public ResponseEntity<TitlesReply> getTitlesSearch (@RequestParam(value="searchValue")  String query) {
    System.out.println("Received A getTitlesSearch Request");
    boolean success = true;
    List<Title> tempTitles = null;
    try{
      tempTitles = this.userService.getTitlesSearch(query);
    } catch (Exception e) {
      success = false;
    } finally {
      if (tempTitles == null) { success = false; }
    }
    List<Title> tempAllTitles = null;
    if (success) {
      try{
        tempAllTitles = userService.getTitles();
      } catch (Exception e) {
        success = false;
      } finally {
        if (tempAllTitles == null) { success = false; }
      }
    }
    TitlesReply response = new TitlesReply();
    response.setTitles(tempTitles);
    response.setAllTitles(tempAllTitles); 
    response.setSuccess(success);
    return new ResponseEntity<>(response, HttpStatus.OK);
    
  }

}
