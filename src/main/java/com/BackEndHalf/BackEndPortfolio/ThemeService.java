package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    return themeRepository.findAll();
  }
  public Themes updateThemes(Themes theme) {
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
