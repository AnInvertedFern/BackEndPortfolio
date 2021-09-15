package com.BackEndHalf.BackEndPortfolio;

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

  public getThemes() {

  }
  public updateThemes() {
    
  }

}
