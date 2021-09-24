package com.BackEndHalf.BackEndPortfolio;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

  @Bean
  public DataSource getDataSource() {
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName("org.postgresql.Driver");
    dataSourceBuilder.url("jdbc:postgresql://localhost:5432/PortfolioDatabase");
    dataSourceBuilder.username("postgres");
    dataSourceBuilder.password("Password");
    return dataSourceBuilder.build();
  }
  
}
