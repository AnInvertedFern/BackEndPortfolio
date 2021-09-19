package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository  extends JpaRepository<SiteUser, Long>{


  @Query("select u from User u where u.firstName = %?1% or u.lastName = %?2% or u.title = %?3% or u.quote = %?4%")
  public List<SiteUser> findAllByAll(String firstName,String lastName, String title, String quote);

  @Query("select u from User u where u.title = %?1%")
  public List<SiteUser> findAllByTitle(String title);


}
