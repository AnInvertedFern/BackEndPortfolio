package com.BackEndHalf.BackEndPortfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ThemeController {
  @Autowired
  private final ThemeService themeService;
  ThemeController(ThemeService themeService) { 
      this.themeService = themeService;
  }

  @GetMapping("/api/themes/all/")
  public getThemes()  {
    
  }
  @PostMapping("/api/themes/")
  public updateThemes () {
    
  }

}
