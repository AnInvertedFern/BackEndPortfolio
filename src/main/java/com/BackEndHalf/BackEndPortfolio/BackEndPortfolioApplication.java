package com.BackEndHalf.BackEndPortfolio;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
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
	@Autowired
	DataSource dataSource;

	@Override
	public void run(String... args) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DROP TABLE IF EXISTS users CASCADE");
		jdbcTemplate.update("DROP TABLE IF EXISTS authorities");
		jdbcTemplate.update("CREATE TABLE users ( username varchar(255) NOT NULL, password varchar(255) NOT NULL, enabled boolean NOT NULL, PRIMARY KEY (username) )");
		jdbcTemplate.update("CREATE TABLE authorities ( username varchar(255) NOT NULL, authority varchar(255) NOT NULL, PRIMARY KEY (username, authority), FOREIGN KEY (username) REFERENCES users (username) ) ");

		jdbcTemplate.update("DROP TABLE IF EXISTS site_user");
		jdbcTemplate.update("DROP TABLE IF EXISTS themes");
		jdbcTemplate.update("DROP SEQUENCE IF EXISTS hibernate_sequence");
		jdbcTemplate.update("CREATE SEQUENCE hibernate_sequence START 1");
		jdbcTemplate.update("CREATE TABLE site_user ( id bigint NOT NULL, card_color varchar(255) NOT NULL, contact_num int NOT NULL, contacts bytea NOT NULL, first_name varchar(255) NOT NULL, last_name varchar(255) NOT NULL, last_theme int NOT NULL, quote varchar(255) NOT NULL, secret varchar(255) NOT NULL, symbol char(1) NOT NULL, symbol_background_color varchar(255) NOT NULL, symbol_color varchar(255) NOT NULL, text_color varchar(255) NOT NULL, title varchar(255) NOT NULL, PRIMARY KEY (id) ) ");
		jdbcTemplate.update("CREATE TABLE themes ( id bigint NOT NULL, active_tab_color varchar(255) NOT NULL, add_user_color varchar(255) NOT NULL, background_color varchar(255) NOT NULL, confirm_theme_color varchar(255) NOT NULL, edit_user_color varchar(255) NOT NULL, footer_seperator_color varchar(255) NOT NULL, inactive_tab_color varchar(255) NOT NULL, input_button_color varchar(255) NOT NULL, input_color varchar(255) NOT NULL, login_shadow_color varchar(255) NOT NULL, logout_button_color varchar(255) NOT NULL, popup_color varchar(255) NOT NULL, refresh_user_color varchar(255) NOT NULL, search_bar_color varchar(255) NOT NULL, search_title_shadow_color varchar(255) NOT NULL, text_color varchar(255) NOT NULL, title_shadow_color varchar(255) NOT NULL, toolbar_color varchar(255) NOT NULL , PRIMARY KEY (id) ) ");

		Themes tempTheme = new Themes(
			"royalblue",
			"yellow",
			"tomato",
			"navy",
			"violet",
			"grey",
			"brown",
			"gold",
			"green",
			"palevioletred",

			"green",
			"beige",
			"blueviolet",
			"black",
			"orange",
			"blue",
			"cornsilk",
			"pink"
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
      "gold",

			"olivedrab",
			"beige",
			"blueviolet",
			"crimson",
			"orangered",
			"white",
			"lemonchiffon",
			"peachpuff"
		);
		themeService.addTheme(tempTheme);
		tempTheme = new Themes(
			"blue",
      "yellow",
      "red",
      "navy",
      "purple",
      "black",
      "orange",
      "firebrick",
      "green",
      "red",

			"indianred",
			"lavenderblush",
			"purple",
			"black",
			"goldenrod",
			"gold",
			"black",
			"pink"
		);
		themeService.addTheme(tempTheme);
		System.out.println("Finished Creating Themes");

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
			"Blue",
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
			"pink",
			"purple"

		);
		userService.addUser(tempUser);
		webSecurityConfig.getUserDetails().createUser(User.withDefaultPasswordEncoder()
		.username( Long.toString( tempUser.getId()) )
		.password("AmSecondUser")
		.roles("ADMIN")
		.build() );
		System.out.println("Jane's user ID is : " +  Long.toString( tempUser.getId()) );


		tempUser = new SiteUser(
			"Fred",
			"Joe",
			"Cook",
			new Long[0],
			0,
			"What? A quote!",
			"Likes broccoli",
			2,
			'C',
			"blue",
			"orange",
			"black",
			"gold"

		);
		userService.addUser(tempUser);
		webSecurityConfig.getUserDetails().createUser(User.withDefaultPasswordEncoder()
		.username( Long.toString( tempUser.getId()) )
		.password("AmThirdUser")
		.roles("USER")
		.build() );
		System.out.println("Fred's user ID is : " +  Long.toString( tempUser.getId()) );

		System.out.println("Finished Creating User");

	}

}
