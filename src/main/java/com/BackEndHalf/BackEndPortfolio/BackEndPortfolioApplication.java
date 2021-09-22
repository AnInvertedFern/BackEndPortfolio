package com.BackEndHalf.BackEndPortfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class BackEndPortfolioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackEndPortfolioApplication.class, args);
	}

	
	@Autowired
	UserService userService;
	@Autowired
	ThemeService themeService;
	@Autowired
	WebSecurityConfig webSecurityConfig;

	@Override
	public void run(String... args) {

		Themes tempTheme = new Themes(
			"blue",
			"yellow",
			"red",
			"navy",
			"purple",
			"grey",
			"brown",
			"gold",
			"green",
			"red"
		);
		themeService.addTheme(tempTheme);
		tempTheme = new Themes(
			"yellow",
      "blue",
      "navy",
      "red",
      "black",
      "purple",
      "lime",
      "brown",
      "green",
      "red"
		);
		themeService.addTheme(tempTheme);
		tempTheme = new Themes(
			"blue",
      "yellow",
      "red",
      "navy",
      "purple",
      "black",
      "white",
      "brown",
      "green",
      "red"
		);
		themeService.addTheme(tempTheme);
		System.out.println("Finished Creating Themes");

		System.out.println("Retriving Themes");
		System.out.println(themeService.getThemes().toString());


		SiteUser tempUser = new SiteUser(
			"Bob",
			"Smith",
			"Cook",
			new Long[0],
			0,
			"Hi, I am Bob",
			"This is a secret",
			2,
			'O',
			"Green",
			"Black",
			"White",
			"purple"

		);
		userService.addUser(tempUser);
		webSecurityConfig.getUserDetails().createUser(User.withDefaultPasswordEncoder()
		.username( Long.toString( tempUser.getId()) )
		.password("AmFirstUser")
		.roles("USER")
		.build() );
		System.out.println("Bob's user ID is : " +  Long.toString( tempUser.getId()) );

		tempUser = new SiteUser(
			"Jane",
			"Smith",
			"Bus Driver",
			new Long[0],
			0,
			"Hi, I am Jane",
			"What secret?",
			1,
			'L',
			"red",
			"blue",
			"white",
			"purple"

		);
		userService.addUser(tempUser);
		webSecurityConfig.getUserDetails().createUser(User.withDefaultPasswordEncoder()
		.username( Long.toString( tempUser.getId()) )
		.password("AmSecondUser")
		.roles("ADMIN")
		.build() );
		System.out.println("Jane's user ID is : " +  Long.toString( tempUser.getId()) );

		System.out.println("Finished Creating User");

		System.out.println("Retriving User");
		// System.out.println(userService.getUsers().toString());

	}

}
