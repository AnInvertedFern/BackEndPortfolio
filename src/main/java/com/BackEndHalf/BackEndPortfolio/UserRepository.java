package com.BackEndHalf.BackEndPortfolio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository  extends JpaRepository<SiteUser, Long>{


  @Query("select u from SiteUser u where u.firstName like %?1% or u.lastName like %?1% or u.title like %?1% or u.quote like %?1%")
  public List<SiteUser> findAllByAll(String query);

  @Query("select u from SiteUser u where u.title like %?1%")
  public List<SiteUser> findAllByTitle(String title);


}
