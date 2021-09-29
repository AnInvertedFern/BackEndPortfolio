package com.BackEndHalf.BackEndPortfolio;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Service
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

  private JdbcUserDetailsManager userDetails;

  @Autowired
  private final DataSource datasource;
  public WebSecurityConfig (DataSource datasource) {
    this.datasource = datasource;
  }

  @Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and()
			.authorizeRequests()
      .antMatchers(HttpMethod.GET, "/api/users/all").permitAll()
      .antMatchers(HttpMethod.POST, "/api/users/addcontact/").authenticated()//.permitAll()//
      .antMatchers(HttpMethod.DELETE, "/api/users/{id}").authenticated()//.permitAll()//
      .antMatchers(HttpMethod.POST, "/api/users/search/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/users/").authenticated()//.permitAll()//
      .antMatchers(HttpMethod.PUT, "/api/users/").permitAll()
      .antMatchers(HttpMethod.GET, "/api/titles/all/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/titles/search/").permitAll()
      .antMatchers(HttpMethod.GET, "/api/themes/all/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/themes/").hasAnyRole("ADMIN")//.permitAll()//
      .antMatchers(HttpMethod.GET, "/login/get_role/").permitAll()
      .antMatchers(HttpMethod.GET, "/logout/").authenticated()//.permitAll()//
      .antMatchers(HttpMethod.GET, "/logout/give_response/").authenticated()//.permitAll()//
				.and()
			.formLogin()
				// .loginPage("/login")
				.loginProcessingUrl("/login/get_role/")
                .permitAll()
				.and()
            // .csrf().disable()
            .csrf(csrf -> csrf.disable())
            .httpBasic().and()
			.logout()
        .logoutUrl("/logout/")
        .logoutSuccessUrl("/logout/give_response/")
				.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
    userDetails = new JdbcUserDetailsManager(this.datasource);
		return userDetails;
	}
  public JdbcUserDetailsManager getUserDetails() {
    return userDetails;
  }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
