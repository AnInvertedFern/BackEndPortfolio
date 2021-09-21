package com.BackEndHalf.BackEndPortfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndPortfolioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackEndPortfolioApplication.class, args);
	}

	
	@Autowired
	UserService userService;
	@Autowired
	ThemeService themeService;

	@Override
	public void run(String... args) {
		SiteUser tempUser = new SiteUser(
			"Bob",
			"Smith",
			"Cook",
			new SiteUser[0],
			0,
			"Hi, I am Bob",
			"This is a secret",
			null,
			'O',
			"Green",
			"Black",
			"White",
			"purple"

		);
		userService.addUser(tempUser);
		tempUser = new SiteUser(
			"Jane",
			"Smith",
			"Bus Driver",
			new SiteUser[0],
			0,
			"Hi, I am Jane",
			"What secret?",
			null,
			'L',
			"red",
			"blue",
			"white",
			"purple"

		);
		userService.addUser(tempUser);
		System.out.println("Finished Creating User");

		System.out.println("Retriving User");
		System.out.println(userService.getUsers().toString());


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

	}

}
