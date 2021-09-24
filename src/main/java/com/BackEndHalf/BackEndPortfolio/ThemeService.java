package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
  
  @Autowired
  private final ThemeRepository themeRepository;
  @Autowired
  private final WebSecurityConfig webSecurityConfig;
  public ThemeService(ThemeRepository themeRepository, WebSecurityConfig webSecurityConfig) {
      this.themeRepository = themeRepository;
      this.webSecurityConfig = webSecurityConfig;
  }

  public List<Themes> getThemes() {
    List<Themes> tempThemes = themeRepository.findAll();
    tempThemes.sort(new Themes() );
    return tempThemes;
  }
  public Themes addTheme(Themes theme) {
    themeRepository.save(theme);
    return theme;
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
  public Themes updateThemes(Themes theme, Principal userDetails) {
    if (userDetails == null) { 
      return null;
    }
    if (!getIsAdmin(userDetails)) {
      return null;
    }

    Themes oldTheme = themeRepository.findById(theme.getId()).orElse(null);
    if (oldTheme != null) {
        // oldUser.setAll(user);
        // userRepository.save(oldUser);
        themeRepository.save(theme);
        return theme;
    } else {
        // userRepository.save(user);
        return null;
    }
    
  }

}
