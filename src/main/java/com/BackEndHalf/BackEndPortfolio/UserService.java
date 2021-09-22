package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final WebSecurityConfig webSecurityConfig;
  @Autowired
  private final ThemeService themeService;
  public UserService(UserRepository userRepository, WebSecurityConfig webSecurityConfig, ThemeService themeService) {
      this.userRepository = userRepository;
      this.webSecurityConfig = webSecurityConfig;
      this.themeService = themeService;
  }
  // List<SiteUser> tempUsers;
  public List<SiteUser> getUsers(Principal userLoggedIn) {
    // this.tempUsers = userRepository.findAll();
    List<SiteUser> tempUsers = userRepository.findAll();
    // System.out.println(tempUsers.size());
    tempUsers = filterInfoByUserAndRole(tempUsers, userLoggedIn);
    // System.out.println(tempUsers.toString());
    return tempUsers;
  }
  private boolean getIsAdmin(Principal userDetails) {
    boolean isAdmin = false;

    Collection<GrantedAuthority> roles = ((UsernamePasswordAuthenticationToken) userDetails ).getAuthorities();
    for (GrantedAuthority authority : roles) {
      if (authority.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
      }
    }
    return isAdmin;
  }
  private List<SiteUser> filterInfoByUserAndRole(List<SiteUser> users, Principal userDetails) {
    if (userDetails == null) { 
      for (SiteUser user : users){
          user.setSecret("***Secret***");
      }
      return users;
    }
    // System.out.println(users.size());
    if ( getIsAdmin(userDetails) ) { return users; }
    for (SiteUser user : users){
      // System.out.println("filter for loop");
      // System.out.println(user.getId() + ", " + Long.parseLong( userDetails.getName()) );
      if ( !user.getId().equals(Long.parseLong( userDetails.getName()) ) ) {
        user.setSecret("***Secret***");
        // System.out.println("***");
        // System.out.println(user.toString());
      }
    }
    // System.out.println(users.toString());
    return users;
  }
  private boolean checkDuplicateContactsAndLogin(SiteUser userAddTo, SiteUser userToAdd, Principal userDetails) {
    if (!userToAdd.getId().equals(Long.parseLong(userDetails.getName())) ) {
      return false;
    }
    if (userToAdd.getId().equals(userAddTo.getId()) ) {
      return false;
    }
    if ( new ArrayList<Long>(Arrays.asList(userAddTo.getContacts())) .indexOf(userToAdd.getId()) != -1) {
      return false;
    }
    return true;

  }
  public SiteUser addContact(SiteUser userAddTo, SiteUser userToAdd, Principal userLoggedIn) {
    if (!checkDuplicateContactsAndLogin(userAddTo, userToAdd, userLoggedIn)) {
      return null; //make a proper error message later
    }
    SiteUser oldUserAddTo = userRepository.findById(userAddTo.getId()).orElse(null);
    // System.out.println(oldUserAddTo.getLastName());
    // System.out.println(userAddTo.getId());
    if (oldUserAddTo != null) {
        // oldUser.setAll(user);
        // userRepository.save(oldUser);
        List<Long> tempContacts = new ArrayList<Long>( Arrays.asList(oldUserAddTo.getContacts()) );
        // System.out.println(userToAdd);
        // System.out.println(tempContacts.add(null));
        tempContacts.add(userToAdd.getId());
        // System.out.println("not see this?");
        oldUserAddTo.setContacts( (Long[]) tempContacts.toArray(new Long[0]) );
        oldUserAddTo.setContactNum(oldUserAddTo.getContactNum()+1);
        // System.out.println(oldUserAddTo.toString());
        userRepository.save(oldUserAddTo);
        return oldUserAddTo;
    } else {
        // userRepository.save(user);
        return null;
    }
    
  }   //call updateUsers
  private boolean checkUpdateUserSelfPermissionAndFields(SiteUser user, Principal userDetails) {
    System.out.println(user.toString());
    System.out.println(userDetails);
    System.out.println(userDetails.getName());
    System.out.println(getIsAdmin(userDetails));
    if (!user.getId().equals(Long.parseLong(userDetails.getName())) && !getIsAdmin(userDetails)) {
      System.out.println(user.getId() +", "+ Long.parseLong(userDetails.getName()) +", "+ getIsAdmin(userDetails));
      System.out.println("here0\n\n\n\n\n\n");
      return false;
    }


    //I am just going to trust that id is correct and not messed with
    //That is a bad assumption, but this is just a small demo piece
    SiteUser oldUser = userRepository.findById(user.getId()).orElse(null);
    if ( !user.getId().equals(oldUser.getId()) ){
      System.out.println("here0\n\n\n\n\n\n");
      return false;
    }
    if ( !user.getLastName().equals(oldUser.getLastName()) ){
      System.out.println("here1\n\n\n\n\n\n");
      return false;
    }
    if ( user.getContactNum() != oldUser.getContactNum() ){
      System.out.println("here2\n\n\n\n\n\n");
      return false;
    }
    if ( user.getContacts().length != oldUser.getContacts().length ){
      System.out.println("here3\n\n\n\n\n\n");
      return false;
    }
    for (int i = 0; i < user.getContacts().length; i++) {
      if (!user.getContacts()[i].equals(oldUser.getContacts()[i]) ) {
        System.out.println(!user.getContacts()[i].equals(oldUser.getContacts()[i]));
        System.out.println(user.getContacts()[i].toString() +", "+i+", "+ oldUser.getContacts()[i].toString());
        System.out.println("here4\n\n\n\n\n\n");
        return false; // This will trigger is the contacts get out of order (e.g. if contact history gets out of order)
      }
    }
    return true;
  }
  public SiteUser updateUsers(SiteUser user, Principal userLoggedIn) {
    if (!checkUpdateUserSelfPermissionAndFields(user, userLoggedIn)) {
      System.out.println("failed permissions and fields check in update user");
      return null; //make a proper error message later
    }
    if (!checkUserConsistancy(user)) {
      System.out.println("failed consistancy check in update user");
      return null; //make a proper error message later
    }
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
  
  private boolean checkUserConsistancy(SiteUser user) {
    if ( !Pattern.compile("[\\-a-zA-Z_ ]+").matcher(user.getFirstName()).matches() ) {
      System.out.println("here1\n\n\n\n\n\n");
      return false;
    }
    System.out.println(Pattern.compile("[-a-zA-Z_ ]+").matcher(user.getFirstName()).matches());
    if ( !Pattern.compile("[-a-zA-Z_ ]+").matcher(user.getLastName()).matches() ) {
      System.out.println("here2\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[-a-zA-Z0-9_() ]+").matcher(user.getTitle()).matches() ) {
      System.out.println("here3\n\n\n\n\n\n");
      return false;
    }
    if ( user.getContactNum() != user.getContacts().length) {
      System.out.println("here4\n\n\n\n\n\n");
      return false;
    }
    
    if ( !Pattern.compile("[-a-zA-Z0-9()_.!,? ]+").matcher(user.getQuote()).matches() ) {
      System.out.println("here5\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[-a-zA-Z0-9()_.!,? ]+").matcher(user.getSecret()).matches() ) {
      System.out.println("here6\n\n\n\n\n\n");
      return false;
    }
    if (user.getLastTheme() < 0 || user.getLastTheme() > themeService.getThemes().size()-1 ) {
      System.out.println("here7\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getSymbolColor()).matches() ) {
      System.out.println("here8\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getCardColor()).matches() ) {
      System.out.println("here9\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getTextColor()).matches() ) {
      System.out.println("here10\n\n\n\n\n\n");
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getSymbolBackgroundColor()).matches() ) {
      System.out.println("here11\n\n\n\n\n\n");
      return false;
    }
    return true;
      

  }
  public SiteUser addUser(SiteUser user) {
    if (!checkUserConsistancy(user)) {
      System.out.println("failed consistancy check in add user");
      return null; //make a proper error message later
    }
    if (user.getContactNum() != 0) {
      return null; //make a proper error message later
    }
    userRepository.save(user);
    System.out.println("added user");
    //also register into websecurity
    return user;
    
  }
  private boolean checkDeleteUserPermissions(Long userID, Principal userDetails) {
    if (!userID.equals(Long.parseLong(userDetails.getName())) && !getIsAdmin(userDetails)) {
      return false;
    }
    return true;

  }
  public String deleteUser(Long userID, Principal userLoggedIn) {
    if (!checkDeleteUserPermissions(userID, userLoggedIn)) {
      return null; //make a proper error message later
    }
    userRepository.deleteById(userID);
    //check credentials and role
    this.webSecurityConfig.getUserDetails().deleteUser(Long.toString(userID));
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
        List<SiteUser> tempUsers = new ArrayList<SiteUser>();
        tempUsers.add(user);
        titlesSeen.add( new Title( user.getTitle(), tempUsers ) );
      }
    }
    return titlesSeen;

  }
  // public searchTitles() {
    
  // }
  public SiteUser attemptLogin_GetRole(Principal user) {
    return userRepository.findById( Long.parseLong(user.getName()) ).orElse(null);
  }
  public SiteUser getUserByID(Long userID) {
    return userRepository.findById(userID).orElse(null);
  }
  public List<SiteUser> getUsersSearch(String query) {
    return userRepository.findAllByAll(query);
    
  }
  public List<Title> getTitlesSearch(String query) {
    List<SiteUser> searchedUsers= userRepository.findAllByTitle(query);
    return constructTItles(searchedUsers);
    
  }
  
  // public TitlesReply getTitlesSearch(String query) {
  //   List<SiteUser> searchedUsers= userRepository.findAllByTitle(query);
  //   List<Title> searchedTitles=  constructTItles(searchedUsers);
  //   TitlesReply response = new TitlesReply("", true, searchedTitles, getTitles());
  //   return response;
    
  // }

}
