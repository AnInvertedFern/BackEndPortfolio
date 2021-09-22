package com.BackEndHalf.BackEndPortfolio;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

  private InMemoryUserDetailsManager userDetails;

  @Override
	protected void configure(HttpSecurity http) throws Exception {
    //withHttpOnlyFalse();


		http.cors().and()
			.authorizeRequests()
      .antMatchers(HttpMethod.GET, "/api/users/all").permitAll()
      .antMatchers(HttpMethod.POST, "/api/users/addcontact/").permitAll()//.authenticated()
      .antMatchers(HttpMethod.DELETE, "/api/users/{id}").permitAll()//.authenticated()//check user or if admin in controller
      .antMatchers(HttpMethod.GET, "/api/users/{ID}/").permitAll()   //delete
      .antMatchers(HttpMethod.POST, "/api/users/search/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/users/search/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/users/").permitAll()//.authenticated() //check user in controller
      //.authenticated still lets people update users without being logged in for some reason
      .antMatchers(HttpMethod.PUT, "/api/users/").permitAll()
      .antMatchers(HttpMethod.GET, "/api/titles/all/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/titles/search/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/titles/search/").permitAll()
      .antMatchers(HttpMethod.GET, "/api/themes/all/").permitAll()
      .antMatchers(HttpMethod.POST, "/api/themes/").hasAnyRole("ADMIN")
      .antMatchers(HttpMethod.GET, "/login/get_role/").permitAll()
      .antMatchers(HttpMethod.GET, "/logout/").permitAll()
      .antMatchers(HttpMethod.GET, "/logout/give_response/").permitAll()
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
		// UserDetails user =
		// 	 User.withDefaultPasswordEncoder()
		// 		.username("1")
		// 		.password("password")
		// 		.roles("ADMIN")
		// 		.build();

    userDetails = new InMemoryUserDetailsManager();
		return userDetails;
	}
  public InMemoryUserDetailsManager getUserDetails() {
    return userDetails;
  }
  public void setUserDetails(InMemoryUserDetailsManager userDetails) {
    this.userDetails = userDetails;
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
