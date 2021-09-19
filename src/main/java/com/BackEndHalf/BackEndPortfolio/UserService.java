package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
  public List<SiteUser> getUsers() {
    return userRepository.findAll();
  }
  public SiteUser addContact(SiteUser userAddTo, SiteUser userToAdd) {
    SiteUser oldUserAddTo = userRepository.findById(userAddTo.getId()).orElse(null);
    if (oldUserAddTo != null) {
        // oldUser.setAll(user);
        // userRepository.save(oldUser);
        oldUserAddTo.getContacts().add(userToAdd);
        oldUserAddTo.setContactNum(oldUserAddTo.getContactNum()+1);
        userRepository.save(oldUserAddTo);
        return oldUserAddTo;
    } else {
        // userRepository.save(user);
        return null;
    }
    
  }   //call updateUsers
  public SiteUser updateUsers(SiteUser user) {
    SiteUser oldUser = userRepository.findById(user.getId()).orElse(null);
    if (oldUser != null) {
        // oldUser.setAll(user);
        // userRepository.save(oldUser);
        userRepository.save(user);
        return user;
    } else {
        // userRepository.save(user);
        return null;
    }
    
  }
  public SiteUser addUser(SiteUser user) {
    userRepository.save(user);
    //also register into websecurity
    return user;
    
  }
  public String deleteUser(Long userID) {
    userRepository.deleteById(userID);
    //check credentials and role
    return "Deleted";
  }
  // public searchUser() {
    
  // }
  public List<Title> getTitles() {
    List<SiteUser> allUsers= userRepository.findAll();
    return constructTItles(allUsers);

  }
  private List<Title> constructTItles( List<SiteUser> allUsers ){
    List<Title> titlesSeen = new ArrayList<>();
    for (SiteUser user : allUsers) {
      int titlesIndex = -1;
      int index = 0;
      for (Title title : titlesSeen) {
        if (user.getTitle() == title.getTitle()){
          titlesIndex = index;
          title.getUsers().add(user);
        }
        index++;
      }
      if (titlesIndex == -1) {
        titlesSeen.add( new Title(user.getTitle(), new ArrayList<SiteUser>()) );
      }
    }
    return titlesSeen;

  }
  // public searchTitles() {
    
  // }
  public SiteUser attemptLogin_GetRole(Principal user) {
    return null;
  }
  public SiteUser getUserByID(Long userID) {
    return userRepository.findById(userID).orElse(null);
  }
  public List<SiteUser> getUsersSearch(String query) {
    return userRepository.findAllByAll(query,query,query,query);
    
  }
  public List<Title> getTitlesSearch(String query) {
    List<SiteUser> searchedUsers= userRepository.findAllByTitle(query);
    return constructTItles(searchedUsers);
    
  }

}
