package com.BackEndHalf.BackEndPortfolio;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<List<Themes>> getThemes()  {
    List<Themes> tempUsers = this.themeService.getThemes();
    return new ResponseEntity<>(tempUsers, HttpStatus.OK);
    
  }
  @PostMapping("/api/themes/")
  public ResponseEntity<Themes> updateThemes (@RequestBody Themes theme, Principal userLoggedIn) {
    Themes tempThemes = themeService.updateThemes(theme, userLoggedIn);
    return new ResponseEntity<>(tempThemes, HttpStatus.OK);
    
  }

}
