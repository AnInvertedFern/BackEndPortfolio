package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Themes, Long> {
  
}
