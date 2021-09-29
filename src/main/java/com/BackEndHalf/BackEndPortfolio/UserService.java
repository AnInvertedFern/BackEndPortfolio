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
  public List<SiteUser> getUsers(Principal userLoggedIn) throws Exception {
    List<SiteUser> tempUsers = userRepository.findAll();
    tempUsers.sort(new SiteUser() );
    tempUsers = filterInfoByUserAndRole(tempUsers, userLoggedIn);
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
    if ( getIsAdmin(userDetails) ) { 
      return users; 
    }
    for (SiteUser user : users){
      if ( !user.getId().equals(Long.parseLong( userDetails.getName()) ) ) {
        user.setSecret("***Secret***");
      }
    }
    return users;
  }
  private boolean checkDuplicateContactsAndLogin(SiteUser userAddTo, SiteUser userToAdd, Principal userDetails) {
    if (userDetails == null || userAddTo == null || userToAdd == null) { 
      return false;
    }
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
  public SiteUser addContact(SiteUser userAddTo, SiteUser userToAdd, Principal userLoggedIn) throws Exception {
    if (!checkDuplicateContactsAndLogin(userAddTo, userToAdd, userLoggedIn)) {
      throw new Exception();
    }
    SiteUser oldUserAddTo = userRepository.findById(userAddTo.getId()).orElse(null);
    if (oldUserAddTo != null) {
        List<Long> tempContacts = new ArrayList<Long>( Arrays.asList(oldUserAddTo.getContacts()) );
        tempContacts.add(userToAdd.getId());
        oldUserAddTo.setContacts( (Long[]) tempContacts.toArray(new Long[0]) );
        oldUserAddTo.setContactNum(oldUserAddTo.getContactNum()+1);
        userRepository.save(oldUserAddTo);
        return oldUserAddTo;
    } else {
        throw new Exception();
    }
  }
  private boolean checkUpdateUserSelfPermissionAndFields(SiteUser user, Principal userDetails) {
    if (userDetails == null || user == null) { 
      return false;
    }
    if (!user.getId().equals(Long.parseLong(userDetails.getName())) && !getIsAdmin(userDetails)) {
      return false;
    }
    SiteUser oldUser = userRepository.findById(user.getId()).orElse(null);
    if ( !user.getId().equals(oldUser.getId()) ){
      return false;
    }
    if ( !user.getLastName().equals(oldUser.getLastName()) && !getIsAdmin(userDetails) ){
      return false;
    }
    if ( user.getContactNum() != oldUser.getContactNum() ){
      return false;
    }
    if ( user.getContacts().length != oldUser.getContacts().length ){
      return false;
    }

    if ( !user.getSecret().equals(oldUser.getSecret()) && !user.getId().equals(Long.parseLong(userDetails.getName()))  ){
      return false;
    }

    for (int i = 0; i < user.getContacts().length; i++) {
      if (!user.getContacts()[i].equals(oldUser.getContacts()[i]) ) {
        return false; // This will trigger is the contacts get out of order (e.g. if contact history gets out of order)
      }
    }
    return true;
  }
  public SiteUser updateUsers(SiteUser user, Principal userLoggedIn) throws Exception {
    if (!checkUpdateUserSelfPermissionAndFields(user, userLoggedIn)) {
      System.out.println("Failed permissions and fields check in update user");
      throw new Exception();
    }
    if (!checkUserConsistancy(user)) {
      System.out.println("Failed consistancy check in update user");
      throw new Exception();
    }
    SiteUser oldUser = userRepository.findById(user.getId()).orElse(null);
    if (oldUser != null) {
        userRepository.save(user);
        return user;
    } else {
        throw new Exception();
    }
  }
  
  private boolean checkUserConsistancy(SiteUser user) {
    if (user == null) { 
      return false;
    }
    if ( !Pattern.compile("[\\-a-zA-Z_ ]+").matcher(user.getFirstName()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[-a-zA-Z_ ]+").matcher(user.getLastName()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[-a-zA-Z0-9_() ]+").matcher(user.getTitle()).matches() ) {
      return false;
    }
    if ( user.getContactNum() != user.getContacts().length) {
      return false;
    }
    
    if ( !Pattern.compile("[-a-zA-Z0-9()_.!,? ]+").matcher(user.getQuote()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[-a-zA-Z0-9()_.!,? ]+").matcher(user.getSecret()).matches() ) {
      return false;
    }
    if (user.getLastTheme() < 0 || user.getLastTheme() > themeService.getThemes().size()-1 ) {
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getSymbolColor()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getCardColor()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getTextColor()).matches() ) {
      return false;
    }
    if ( !Pattern.compile("[a-zA-Z]+").matcher(user.getSymbolBackgroundColor()).matches() ) {
      return false;
    }
    return true;
      

  }
  public SiteUser addUser(SiteUser user) throws Exception {
    if (!checkUserConsistancy(user)) {
      System.out.println("Failed consistancy check in add user");
      throw new Exception();
    }
    if (user.getContactNum() != 0) {
      throw new Exception();
    }
    userRepository.save(user);
    return user;
    
  }
  private boolean checkDeleteUserPermissions(Long userID, Principal userDetails) {
    if (userDetails == null) { 
      return false;
    }
    if (!userID.equals(Long.parseLong(userDetails.getName())) && !getIsAdmin(userDetails)) {
      return false;
    }
    return true;

  }
  public String deleteUser(Long userID, Principal userLoggedIn) throws Exception {
    if (!checkDeleteUserPermissions(userID, userLoggedIn)) {
      throw new Exception();
    }
    userRepository.deleteById(userID);
    this.webSecurityConfig.getUserDetails().deleteUser(Long.toString(userID));
    return Long.toString(userID);
  }
 public void removeUserOnFail(Long userID) {
  userRepository.deleteById(userID);
 }
  public List<Title> getTitles() throws Exception{
    List<SiteUser> allUsers= userRepository.findAll();
    List<Title> tempTitles= constructTItles(allUsers);
    tempTitles.sort(new Title() );
    return tempTitles;

  }
  private List<Title> constructTItles( List<SiteUser> allUsers ){
    List<Title> titlesSeen = new ArrayList<>();
    for (SiteUser user : allUsers) {
      int titlesIndex = -1;
      int index = 0;
      for (Title title : titlesSeen) {
        if ( user.getTitle().equals(title.getTitle()) ){
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
  public SiteUser attemptLogin_GetRole(Principal user) throws Exception {
    if (user == null) {
      throw new Exception();
    }
    return userRepository.findById( Long.parseLong(user.getName()) ).orElse(null);
  }
  private boolean checkGoodQuery(String query) {
    if ( !Pattern.compile("[-a-zA-Z0-9()_.!,? ]+").matcher(query).matches() ) {
      return false;
    }
    return true;
  }
  public List<SiteUser> getUsersSearch(String query, Principal userLoggedIn) throws Exception {
    if (!checkGoodQuery(query)) {
      throw new Exception();
    }
    List<SiteUser> tempUsers = userRepository.findAllByAll(query);
    tempUsers.sort(new SiteUser() );
    tempUsers = filterInfoByUserAndRole(tempUsers, userLoggedIn);
    return tempUsers;
    
  }
  public List<Title> getTitlesSearch(String query) throws Exception {
    if (!checkGoodQuery(query)) {
      throw new Exception();
    }
    List<SiteUser> searchedUsers= userRepository.findAllByTitle(query);
    searchedUsers = filterInfoByUserAndRole(searchedUsers, null);
    List<Title> tempTitles= constructTItles(searchedUsers);
    tempTitles.sort(new Title() );
    return tempTitles;
    
  }

}
