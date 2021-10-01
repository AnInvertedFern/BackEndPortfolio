package com.BackEndHalf.BackEndPortfolio;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sun.security.auth.UserPrincipal;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BackEndPortfolioTests {

	@BeforeEach
	void reset(@Autowired UserService userService, @Autowired ThemeService themeService, @Autowired WebSecurityConfig webSecurityConfig, @Autowired DataSource dataSource) throws Exception{
		
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
		jdbcTemplate.update("CREATE TABLE themes ( id bigint NOT NULL, active_tab_color varchar(255) NOT NULL, add_user_color varchar(255) NOT NULL, background_color varchar(255) NOT NULL, confirm_theme_color varchar(255) NOT NULL, edit_user_color varchar(255) NOT NULL, footer_seperator_color varchar(255) NOT NULL, inactive_tab_color varchar(255) NOT NULL, input_button_color varchar(255) NOT NULL, input_color varchar(255) NOT NULL, login_shadow_color varchar(255) NOT NULL, login_button_color varchar(255) NOT NULL, popup_color varchar(255) NOT NULL, refresh_user_color varchar(255) NOT NULL, search_bar_color varchar(255) NOT NULL, search_title_shadow_color varchar(255) NOT NULL, text_color varchar(255) NOT NULL, title_shadow_color varchar(255) NOT NULL, toolbar_color varchar(255) NOT NULL , PRIMARY KEY (id) ) ");

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

	@Test
	void userControllerGetUserTests(@Autowired MockMvc mvc) throws Exception{
		assert( mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString().contains("Bob") );
		ObjectMapper mapper = new ObjectMapper();
		UserReply userReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userReply.isSuccess());
		assert(userReply.getAllUsers().size()==3);
		assert(userReply.getAllUsers().get(0).getId()==4);
		assert(userReply.getAllUsers().get(0).getSecret().equals("***Secret***"));

		HttpHeaders headersAdmin = new HttpHeaders();
		headersAdmin.setBasicAuth("5", "AmSecondUser");
		UserReply userReplyAdmin = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersAdmin)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userReplyAdmin.isSuccess());
		assert(userReplyAdmin.getAllUsers().size()==3);
		assert(userReplyAdmin.getAllUsers().get(0).getId()==4);
		assert(!userReplyAdmin.getAllUsers().get(0).getSecret().equals("***Secret***"));

		HttpHeaders headersBob = new HttpHeaders();
		headersBob.setBasicAuth("4", "AmFirstUser");
		UserReply userReplyBob = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersBob)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userReplyBob.isSuccess());
		assert(userReplyBob.getAllUsers().size()==3);
		assert(userReplyBob.getAllUsers().get(0).getId()==4);
		assert(!userReplyBob.getAllUsers().get(0).getSecret().equals("***Secret***"));
		assert(userReplyBob.getAllUsers().get(1).getId()==5);
		assert(userReplyBob.getAllUsers().get(1).getSecret().equals("***Secret***"));
	}

	@Test
	void userServiceGetUserTests(@Autowired UserService userService) throws Exception {
		List<SiteUser> noLoginGetAllUsers = userService.getUsers(null);
		assert( noLoginGetAllUsers != null);
		assert(noLoginGetAllUsers.size()==3);
		assert(noLoginGetAllUsers.get(0).getId()==4);
		assert(noLoginGetAllUsers.get(0).getSecret().equals("***Secret***"));

		ArrayList<GrantedAuthority> tempAuthArray = new ArrayList<GrantedAuthority>();
		tempAuthArray.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
		UsernamePasswordAuthenticationToken adminLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, tempAuthArray );
		List<SiteUser> adminGetAllUsers = userService.getUsers(adminLogin);
		assert(adminGetAllUsers.size()==3);
		assert(adminGetAllUsers.get(0).getId()==4);
		assert(!adminGetAllUsers.get(0).getSecret().equals("***Secret***"));

		ArrayList<GrantedAuthority> tempBobArray = new ArrayList<GrantedAuthority>();
		tempBobArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken bobLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("4"), (Object) null, tempBobArray );
		List<SiteUser> bobGetAllUsers = userService.getUsers(bobLogin);
		assert(bobGetAllUsers.size()==3);
		assert(bobGetAllUsers.get(0).getId()==4);
		assert(bobGetAllUsers.get(0).getFirstName().equals("Bob") );
		assert(!bobGetAllUsers.get(0).getSecret().equals("***Secret***"));

		ArrayList<GrantedAuthority> tempJaneArray = new ArrayList<GrantedAuthority>();
		tempJaneArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken janeLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, tempJaneArray );
		List<SiteUser> janeGetAllUsers = userService.getUsers(janeLogin);
		assert(janeGetAllUsers.size()==3);
		assert(janeGetAllUsers.get(0).getId()==4);
		assert(janeGetAllUsers.get(0).getFirstName().equals("Bob") );
		assert(janeGetAllUsers.get(0).getSecret().equals("***Secret***"));
	}

	@Test
	void userControllerUserSearchTests(@Autowired MockMvc mvc) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		UserReply userSearchReply = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "Smith")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchReply.isSuccess());
		assert(userSearchReply.getUsers().size() == 2);
		assert(userSearchReply.getUsers().get(0).getId()==4);
		assert(userSearchReply.getUsers().get(0).getSecret().equals("***Secret***"));
		assert(userSearchReply.getUsers().get(1).getId()==5);
		assert(userSearchReply.getUsers().get(1).getFirstName().equals("Jane"));
		
		UserReply userSearchEmpty = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "NothingWillMatchThisString")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchEmpty.isSuccess());
		assert(userSearchEmpty.getUsers().size() == 0);

		UserReply userSearchReplyHi = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "Hi, I am J")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchReplyHi.isSuccess());
		assert(userSearchReplyHi.getUsers().size() == 1);
		assert(userSearchReplyHi.getUsers().get(0).getId()==5);
		assert(userSearchReplyHi.getUsers().get(0).getFirstName().equals("Jane"));
		assert(userSearchReplyHi.getUsers().get(0).getSecret().equals("***Secret***"));
		
		HttpHeaders headersAdmin = new HttpHeaders();
		headersAdmin.setBasicAuth("5", "AmSecondUser");
		UserReply userSearchReplyAdmin = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "Hi, I am B").headers(headersAdmin)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchReplyAdmin.isSuccess());
		assert(userSearchReplyAdmin.getUsers().size() == 1);
		assert(userSearchReplyAdmin.getUsers().get(0).getId()==4);
		assert(userSearchReplyAdmin.getUsers().get(0).getFirstName().equals("Bob"));
		assert(!userSearchReplyAdmin.getUsers().get(0).getSecret().equals("***Secret***"));
		
		HttpHeaders headersBob = new HttpHeaders();
		headersBob.setBasicAuth("4", "AmFirstUser");
		UserReply userSearchReplyBob = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "Hi, I").headers(headersBob)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchReplyBob.isSuccess());
		assert(userSearchReplyBob.getUsers().size() == 2);
		assert(userSearchReplyBob.getUsers().get(0).getId()==4);
		assert(userSearchReplyBob.getUsers().get(0).getFirstName().equals("Bob"));
		assert(!userSearchReplyBob.getUsers().get(0).getSecret().equals("***Secret***"));
		assert(userSearchReplyBob.getUsers().get(1).getId()==5);
		assert(userSearchReplyBob.getUsers().get(1).getFirstName().equals("Jane"));
		assert(userSearchReplyBob.getUsers().get(1).getSecret().equals("***Secret***"));
		
		UserReply userSearchReplyCook = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "Cook")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(userSearchReplyCook.isSuccess());
		assert(userSearchReplyCook.getUsers().size() == 2);
		assert(userSearchReplyCook.getUsers().get(0).getId()==4);
		assert(userSearchReplyCook.getUsers().get(0).getFirstName().equals("Bob"));
		assert(userSearchReplyCook.getUsers().get(0).getSecret().equals("***Secret***"));
		assert(userSearchReplyCook.getUsers().get(1).getId()==6);
		assert(userSearchReplyCook.getUsers().get(1).getFirstName().equals("Fred"));
		assert(userSearchReplyCook.getUsers().get(1).getSecret().equals("***Secret***"));
		
		UserReply userSearchReplyError = mapper.readValue(mvc.perform(post("/api/users/search/").param("searchValue", "<")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!userSearchReplyError.isSuccess());
		assert(userSearchReplyError.getUsers() == null);
	}

	@Test
	void userServiceUserSearchTests(@Autowired UserService userService) throws Exception {
		List<SiteUser> noLoginSearchUsers = userService.getUsersSearch("Smith", null);
		assert( noLoginSearchUsers != null);
		assert(noLoginSearchUsers.size()==2);
		assert(noLoginSearchUsers.get(0).getId()==4);
		assert(noLoginSearchUsers.get(0).getSecret().equals("***Secret***"));
		
		List<SiteUser> searchUsersEmpty = userService.getUsersSearch("NothingWillMatchThisString", null);
		assert( searchUsersEmpty != null);
		assert(searchUsersEmpty.size()==0);
		
		boolean errored = false;
		List<SiteUser> searchUsersError;
		try{
			searchUsersError = userService.getUsersSearch("<", null);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
		
		ArrayList<GrantedAuthority> tempAuthArray = new ArrayList<GrantedAuthority>();
		tempAuthArray.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
		UsernamePasswordAuthenticationToken adminLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, tempAuthArray );
		List<SiteUser> adminSearchUsers = userService.getUsersSearch("Hi, I am B", adminLogin);
		assert(adminSearchUsers.size()==1);
		assert(adminSearchUsers.get(0).getId()==4);
		assert(adminSearchUsers.get(0).getFirstName().equals("Bob"));
		assert(!adminSearchUsers.get(0).getSecret().equals("***Secret***"));
		
		ArrayList<GrantedAuthority> tempBobArray = new ArrayList<GrantedAuthority>();
		tempBobArray.add( new SimpleGrantedAuthority("ROLE_User") );
		UsernamePasswordAuthenticationToken bobLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("4"), (Object) null, tempBobArray );
		List<SiteUser> bobSearchUsers = userService.getUsersSearch("Hi, I am ", bobLogin);
		assert(bobSearchUsers.size()==2);
		assert(bobSearchUsers.get(0).getId()==4);
		assert(bobSearchUsers.get(0).getFirstName().equals("Bob"));
		assert(!bobSearchUsers.get(0).getSecret().equals("***Secret***"));
		assert(bobSearchUsers.get(1).getId()==5);
		assert(bobSearchUsers.get(1).getFirstName().equals("Jane"));
		assert(bobSearchUsers.get(1).getSecret().equals("***Secret***"));
		
		List<SiteUser> searchUsersCook = userService.getUsersSearch("Cook", null);
		assert(searchUsersCook.size() == 2);
		assert(searchUsersCook.get(0).getId()==4);
		assert(searchUsersCook.get(0).getFirstName().equals("Bob"));
		assert(searchUsersCook.get(0).getSecret().equals("***Secret***"));
		assert(searchUsersCook.get(1).getId()==6);
		assert(searchUsersCook.get(1).getFirstName().equals("Fred"));
		assert(searchUsersCook.get(1).getSecret().equals("***Secret***"));
	}

	@Test
	void userControllerGetTitlesTests(@Autowired MockMvc mvc) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		TitlesReply titlesReply = mapper.readValue(mvc.perform(get("/api/titles/all/")).andReturn().getResponse().getContentAsString(), TitlesReply.class);
		assert(titlesReply.isSuccess());
		assert(titlesReply.getAllTitles().size() == 2);
		assert(titlesReply.getAllTitles().get(0).getTitle().equals("Cook") || titlesReply.getAllTitles().get(1).getTitle().equals("Cook"));
		assert(titlesReply.getAllTitles().get(0).getTitle().equals("Bus Driver") || titlesReply.getAllTitles().get(1).getTitle().equals("Bus Driver"));
		Title titlesReplyCook;
		Title titlesReplyBus;
		if (titlesReply.getAllTitles().get(0).getTitle().equals("Cook")) {
			titlesReplyCook = titlesReply.getAllTitles().get(0);
			titlesReplyBus = titlesReply.getAllTitles().get(1);
		} else {
			titlesReplyCook = titlesReply.getAllTitles().get(1);
			titlesReplyBus = titlesReply.getAllTitles().get(0);
		}
		assert( titlesReplyBus.getUsers().get(0).getFirstName().equals("Jane") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Fred") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Fred") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Bob") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Bob") );
	}
	
	@Test
	void userServiceGetTitlesTests(@Autowired UserService userService) throws Exception{
		List<Title> gotTitles = userService.getTitles();

		assert(gotTitles.size() == 2);
		assert(gotTitles.get(0).getTitle().equals("Cook") || gotTitles.get(1).getTitle().equals("Cook"));
		assert(gotTitles.get(0).getTitle().equals("Bus Driver") || gotTitles.get(1).getTitle().equals("Bus Driver"));
		Title titlesReplyCook;
		Title titlesReplyBus;
		if (gotTitles.get(0).getTitle().equals("Cook")) {
			titlesReplyCook = gotTitles.get(0);
			titlesReplyBus = gotTitles.get(1);
		} else {
			titlesReplyCook = gotTitles.get(1);
			titlesReplyBus = gotTitles.get(0);
		}
		assert( titlesReplyBus.getUsers().get(0).getFirstName().equals("Jane") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Fred") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Fred") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Bob") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Bob") );
	}
	
	@Test
	void userControllerTitlesSearchTests(@Autowired MockMvc mvc, @Autowired UserService userService) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		TitlesReply titlesSearchReply = mapper.readValue(mvc.perform(post("/api/titles/search/").param("searchValue", "o")).andReturn().getResponse().getContentAsString(), TitlesReply.class);
		assert(titlesSearchReply.isSuccess());
		assert(titlesSearchReply.getAllTitles().size() == 2);
		assert(titlesSearchReply.getTitles().size() == 1);
		assert(titlesSearchReply.getTitles().get(0).getUsers().size()==2);
		assert(titlesSearchReply.getTitles().get(0).getTitle().equals("Cook"));
		assert(titlesSearchReply.getTitles().get(0).getUsers().get(0).getSecret().equals("***Secret***"));

		HttpHeaders headersAdmin = new HttpHeaders();
		headersAdmin.setBasicAuth("5", "AmSecondUser");
		TitlesReply titlesSearchReplyAdmin = mapper.readValue(mvc.perform(post("/api/titles/search/").param("searchValue", "o").headers(headersAdmin)).andReturn().getResponse().getContentAsString(), TitlesReply.class);
		assert(titlesSearchReplyAdmin.isSuccess());
		assert(titlesSearchReplyAdmin.getTitles().get(0).getUsers().get(0).getSecret().equals("***Secret***"));
		
		TitlesReply titlesSearchReplyError = mapper.readValue(mvc.perform(post("/api/titles/search/").param("searchValue", "<")).andReturn().getResponse().getContentAsString(), TitlesReply.class);
		assert(!titlesSearchReplyError.isSuccess());
		assert(titlesSearchReplyError.getAllTitles() == null);
		assert(titlesSearchReplyError.getTitles() == null);

		SiteUser tempUser = new SiteUser(
			"Jessie",
			"Kal",
			"Spooky",
			new Long[0],
			0,
			"I am a GGGhost",
			"Not actually a ghost",
			1,
			'G',
			"crimson",
			"teal",
			"gold",
			"red"
		);
		userService.addUser(tempUser);

		TitlesReply titlesSearchReplyOok = mapper.readValue(mvc.perform(post("/api/titles/search/").param("searchValue", "ook")).andReturn().getResponse().getContentAsString(), TitlesReply.class);
		assert(titlesSearchReplyOok.isSuccess());
		assert(titlesSearchReplyOok.getAllTitles().size() == 3);
		assert(titlesSearchReplyOok.getTitles().size() == 2);
	}
	
	@Test
	void userServiceTitlesSearchTests(@Autowired UserService userService) throws Exception{
		List<Title> gotSearchTitles = userService.getTitlesSearch("Driv");
		assert(gotSearchTitles.size() == 1);
		assert(gotSearchTitles.get(0).getUsers().size()==1);
		assert(gotSearchTitles.get(0).getTitle().equals("Bus Driver"));
		assert(gotSearchTitles.get(0).getUsers().get(0).getSecret().equals("***Secret***"));

		boolean errored = false;
		List<Title> searchTitlesError;
		try{
			searchTitlesError = userService.getTitlesSearch("<");
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
		
		SiteUser tempUser = new SiteUser(
			"Jessie",
			"Kal",
			"Spooky",
			new Long[0],
			0,
			"I am a GGGhost",
			"Not actually a ghost",
			1,
			'G',
			"crimson",
			"teal",
			"gold",
			"red"
		);
		userService.addUser(tempUser);

		List<Title> gotSearchTitlesOok = userService.getTitlesSearch("o");
		assert(gotSearchTitlesOok.size() == 2);
		
		Title titlesReplyCook;
		Title titlesReplySpooky;
		if (gotSearchTitlesOok.get(0).getTitle().equals("Cook")) {
			titlesReplyCook = gotSearchTitlesOok.get(0);
			titlesReplySpooky = gotSearchTitlesOok.get(1);
		} else {
			titlesReplyCook = gotSearchTitlesOok.get(1);
			titlesReplySpooky = gotSearchTitlesOok.get(0);
		}
		assert( titlesReplySpooky.getUsers().get(0).getFirstName().equals("Jessie") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Fred") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Fred") );
		assert( titlesReplyCook.getUsers().get(0).getFirstName().equals("Bob") || titlesReplyCook.getUsers().get(1).getFirstName().equals("Bob") );
	}
	
	@Test
	void userControllerAddUserTests(@Autowired MockMvc mvc) throws Exception{
		ObjectMapper mapper = new ObjectMapper();

		SiteUser tempUser = new SiteUser(
			"Jessie",
			"Kal",
			"Spooky",
			new Long[0],
			0,
			"I am a GGGhost",
			"Not actually a ghost",
			1,
			'G',
			"crimson",
			"teal",
			"gold",
			"red"
		);
		RESTRequest addUserRequest = new RESTRequest("AmFourthUser", tempUser, null);

		String addBody = mapper.writeValueAsString(addUserRequest);
		UserReply addUserReply = mapper.readValue(mvc.perform(put("/api/users/").contentType("application/json").content(addBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(addUserReply.isSuccess());
		assert(addUserReply.getUsers().size() == 1);
		assert(addUserReply.getUsers().get(0).getId() == 7);
		assert(addUserReply.getUsers().get(0).getFirstName().equals("Jessie"));

		UserReply allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==4);
		assert(allUserReply.getAllUsers().get(3).getId()==7);
		assert(allUserReply.getAllUsers().get(3).getSecret().equals("***Secret***"));

		HttpHeaders headersJessie = new HttpHeaders();
		headersJessie.setBasicAuth("7", "AmFourthUser");
		UserReply jessieUserReply = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersJessie)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(jessieUserReply.isSuccess());
		assert(jessieUserReply.getAllUsers().size()==4);
		assert(jessieUserReply.getAllUsers().get(3).getId()==7);
		assert(!jessieUserReply.getAllUsers().get(3).getSecret().equals("***Secret***"));
		assert(jessieUserReply.getAllUsers().get(2).getSecret().equals("***Secret***"));

		tempUser = new SiteUser(
			"Jessie1",
			"Kal1",
			"Spooky1",
			new Long[0],
			2,
			"I am a GGGhost1",
			"Not actually a ghost1",
			1,
			'G',
			"red",
			"brown",
			"grey",
			"black"
		);
		addUserRequest = new RESTRequest("AmFifthUser", tempUser, null);

		addBody = mapper.writeValueAsString(addUserRequest);
		UserReply addUserReplyBadContact = mapper.readValue(mvc.perform(put("/api/users/").contentType("application/json").content(addBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!addUserReplyBadContact.isSuccess());

		tempUser = new SiteUser(
			"Jessie1",
			"Kal1",
			"Spooky1",
			new Long[0],
			0,
			"I am a GGGhost1%",
			"Not actually a ghost1",
			1,
			'G',
			"red",
			"brown",
			"grey",
			"black"
		);
		addUserRequest = new RESTRequest("AmFifthUser", tempUser, null);
		addBody = mapper.writeValueAsString(addUserRequest);
		UserReply addUserReplyBadTexts = mapper.readValue(mvc.perform(put("/api/users/").contentType("application/json").content(addBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!addUserReplyBadTexts.isSuccess());
		
		addUserRequest = new RESTRequest("AmFifthUser", null, null);
		addBody = mapper.writeValueAsString(addUserRequest);
		UserReply addUserReplyBadNull = mapper.readValue(mvc.perform(put("/api/users/").contentType("application/json").content(addBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!addUserReplyBadNull.isSuccess());
		
		HttpHeaders headersJessieFail = new HttpHeaders();
		headersJessieFail.setBasicAuth("8", "AmFifthUser");
		assert(mvc.perform(get("/api/users/all").headers(headersJessieFail)).andReturn().getResponse().getStatus() == 401);
	}
	
	@Test
	void userServiceAddUserTests(@Autowired UserService userService) throws Exception{
		SiteUser tempUser = new SiteUser(
			"Jessie",
			"Kal",
			"Spooky",
			new Long[0],
			0,
			"I am a GGGhost",
			"Not actually a ghost",
			1,
			'G',
			"crimson",
			"teal",
			"gold",
			"red"
		);
		SiteUser addedUser = userService.addUser(tempUser);
		assert(addedUser.getId() == 7);
		assert(addedUser.getFirstName().equals("Jessie"));
		
		List<SiteUser> allUsers = userService.getUsers(null);
		assert(allUsers.size()==4);
		assert(allUsers.get(3).getId()==7);
		assert(allUsers.get(3).getSecret().equals("***Secret***"));
		
		ArrayList<GrantedAuthority> jessieAuthArray = new ArrayList<GrantedAuthority>();
		jessieAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken jessieLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("7"), (Object) null, jessieAuthArray );
		List<SiteUser> jessieGetAllUsers = userService.getUsers(jessieLogin);

		assert(jessieGetAllUsers.size()==4);
		assert(jessieGetAllUsers.get(3).getId()==7);
		assert(jessieGetAllUsers.get(3).getSecret().equals("Not actually a ghost"));
		assert(jessieGetAllUsers.get(2).getSecret().equals("***Secret***"));

		tempUser = new SiteUser(
			"Jessie1",
			"Kal1",
			"Spooky1",
			new Long[1],
			0,
			"I am a GGGhost1",
			"Not actually a ghost1",
			1,
			'G',
			"red",
			"brown",
			"grey",
			"black"
		);
		
		boolean errored = false;
		SiteUser addedUserDeniedByContacts;
		try{
			addedUserDeniedByContacts = userService.addUser(tempUser);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);

		tempUser = new SiteUser(
			"Jessie1",
			"Kal1",
			"Spooky1",
			new Long[0],
			0,
			"I am a GGGhost1",
			"Not actually a ghost1",
			1,
			'G',
			"red",
			"brown^",
			"grey",
			"black"
		);
		
		errored = false;
		SiteUser addedUserDeniedByColorText;
		try{
			addedUserDeniedByColorText = userService.addUser(tempUser);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
		
		errored = false;
		SiteUser addedUserDeniedByNull;
		try{
			addedUserDeniedByNull = userService.addUser(null);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
	}
	
	@Test
	void userControllerUpdateUserTests(@Autowired MockMvc mvc) throws Exception{
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headersJane = new HttpHeaders();
		headersJane.setBasicAuth("5", "AmSecondUser");
		UserReply allUserReply = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersJane)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==3);
		assert(allUserReply.getAllUsers().get(0).getId()==4);
		assert(allUserReply.getAllUsers().get(0).getFirstName().equals("Bob"));
		assert(!allUserReply.getAllUsers().get(0).getSecret().equals("***Secret***"));

		SiteUser tempBob = allUserReply.getAllUsers().get(0);
		tempBob.setFirstName("Robert");
		tempBob.setQuote("A quote no more");
		tempBob.setSecret("He can change his secret");
		
		String updateBody = mapper.writeValueAsString(tempBob);
		HttpHeaders headersRobert = new HttpHeaders();
		headersRobert.setBasicAuth("4", "AmFirstUser");
		UserReply RobertUserReply = mapper.readValue(mvc.perform(post("/api/users/").headers(headersRobert).contentType("application/json").content(updateBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(RobertUserReply.isSuccess());
		assert(RobertUserReply.getUsers().size()==1);
		assert(RobertUserReply.getUsers().get(0).getId()==4);
		assert(RobertUserReply.getUsers().get(0).getFirstName().equals("Robert"));

		allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==3);
		assert(allUserReply.getAllUsers().get(0).getId()==4);
		assert(allUserReply.getAllUsers().get(0).getFirstName().equals("Robert"));
		assert(allUserReply.getAllUsers().get(0).getQuote().equals("A quote no more"));
		assert(allUserReply.getAllUsers().get(0).getSecret().equals("***Secret***"));

		headersRobert = new HttpHeaders();
		headersRobert.setBasicAuth("4", "AmFirstUser");
		UserReply allUserReplyRobert = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersRobert)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReplyRobert.isSuccess());
		assert(allUserReplyRobert.getAllUsers().size()==3);
		assert(allUserReplyRobert.getAllUsers().get(0).getSecret().equals("He can change his secret"));

		tempBob.setSecret("He can change his secret again?");
		updateBody = mapper.writeValueAsString(tempBob);
		HttpHeaders headersRobertFail = new HttpHeaders();
		headersRobert.setBasicAuth("5", "AmSecondUser");
		UserReply RobertFailUserReply = mapper.readValue(mvc.perform(post("/api/users/").headers(headersRobert).contentType("application/json").content(updateBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!RobertFailUserReply.isSuccess());

		tempBob.setLastName("No_Name");
		tempBob.setSecret("He can change his secret");
		updateBody = mapper.writeValueAsString(tempBob);
		headersRobertFail = new HttpHeaders();
		headersRobert.setBasicAuth("4", "AmFirstUser");
		RobertFailUserReply = mapper.readValue(mvc.perform(post("/api/users/").headers(headersRobert).contentType("application/json").content(updateBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!RobertFailUserReply.isSuccess());

		tempBob.setLastName("No_Name");
		updateBody = mapper.writeValueAsString(tempBob);
		headersRobertFail = new HttpHeaders();
		headersRobert.setBasicAuth("5", "AmSecondUser");
		RobertFailUserReply = mapper.readValue(mvc.perform(post("/api/users/").headers(headersRobert).contentType("application/json").content(updateBody)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(RobertFailUserReply.isSuccess());
		headersRobert = new HttpHeaders();
		headersRobert.setBasicAuth("4", "AmFirstUser");
		allUserReply = mapper.readValue(mvc.perform(get("/api/users/all").headers(headersRobert)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().get(0).getLastName().equals("No_Name"));
		assert(allUserReplyRobert.getAllUsers().get(0).getSecret().equals("He can change his secret"));
	}
	
	@Test
	void userServiceUpdateUserTests(@Autowired UserService userService) throws Exception{

		ArrayList<GrantedAuthority> janeAuthArray = new ArrayList<GrantedAuthority>();
		janeAuthArray.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
		UsernamePasswordAuthenticationToken janeLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, janeAuthArray ); 
		List<SiteUser> allUsers = userService.getUsers(janeLogin);
		assert(allUsers.size()==3);
		assert(allUsers.get(1).getId()==5);
		assert(allUsers.get(1).getFirstName().equals("Jane"));

		SiteUser tempJane = allUsers.get(1); 

		tempJane.setFirstName("Rebecca");
		SiteUser updatedUserJane = userService.updateUsers(tempJane, janeLogin);
		assert(updatedUserJane.getId() == 5);
		assert(updatedUserJane.getFirstName().equals("Rebecca"));
		
		allUsers = userService.getUsers(janeLogin);
		assert(allUsers.size()==3);
		assert(allUsers.get(1).getId()==5);
		assert(allUsers.get(1).getFirstName().equals("Rebecca"));

		//ReflectionTestUtils can't change the id field
		//So this part of the test cannot be run

		// ReflectionTestUtils.setField(tempJane, "id", 9, Long.class);
		// assert(tempJane.getId() == 9);

		// boolean errored = false;
		// SiteUser updatedUserFailId;
		// try{
		// 	updatedUserFailId = userService.updateUsers(tempJane, janeLogin);
		// } catch (Exception e) {
		// 	errored = true;
		// }
		// assert(errored);

		
		// ReflectionTestUtils.setField(tempJane, "id", 5, Long.class);
		// assert(tempJane.getId() == 5);
		tempJane.setQuote("A Different Quote");

		ArrayList<GrantedAuthority> fredAuthArray = new ArrayList<GrantedAuthority>();
		fredAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken fredLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("6"), (Object) null, fredAuthArray ); 
		boolean errored = false;
		SiteUser updatedUserFailLogin;
		try{
			updatedUserFailLogin = userService.updateUsers(tempJane, fredLogin);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
		
		SiteUser updatedUserJaneQuote = userService.updateUsers(tempJane, janeLogin);
		assert(updatedUserJaneQuote.getId() == 5);
		assert(updatedUserJaneQuote.getFirstName().equals("Rebecca"));
		assert(updatedUserJaneQuote.getQuote().equals("A Different Quote"));
		
		allUsers = userService.getUsers(janeLogin);
		assert(allUsers.size()==3);
		assert(allUsers.get(1).getId()==5);
		assert(allUsers.get(1).getFirstName().equals("Rebecca"));
		assert(allUsers.get(1).getQuote().equals("A Different Quote"));
	}
	
	@Test
	void userControllerAddContactTests(@Autowired MockMvc mvc) throws Exception{

		ObjectMapper mapper = new ObjectMapper();
		UserReply allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==3);
		assert(allUserReply.getAllUsers().get(0).getId()==4);
		assert(allUserReply.getAllUsers().get(2).getId()==6);
		SiteUser tempBob = allUserReply.getAllUsers().get(0);
		SiteUser tempFred = allUserReply.getAllUsers().get(2);

		HttpHeaders headersFred = new HttpHeaders();
		headersFred.setBasicAuth("6", "AmThirdUser");
		RESTRequest addUserRequest = new RESTRequest("", tempBob, tempFred);
		String addContactBody = mapper.writeValueAsString(addUserRequest);
		UserReply addContactsReply = mapper.readValue(mvc.perform(post("/api/users/addcontact/").contentType("application/json").content(addContactBody).headers(headersFred)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(addContactsReply.isSuccess());
		assert(addContactsReply.getUsers().size()==1);
		assert(addContactsReply.getUsers().get(0).getId()==4);
		
		allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().get(0).getContactNum() == 1);
		assert(allUserReply.getAllUsers().get(0).getContacts()[0] == 6);
		tempBob = allUserReply.getAllUsers().get(0);
		tempFred = allUserReply.getAllUsers().get(2);
		addUserRequest = new RESTRequest("", tempBob, tempFred);
		addContactBody = mapper.writeValueAsString(addUserRequest);
		
		UserReply addContactsReplyRedoFail = mapper.readValue(mvc.perform(post("/api/users/addcontact/").contentType("application/json").content(addContactBody).headers(headersFred)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!addContactsReplyRedoFail.isSuccess());
		
		HttpHeaders headersJane = new HttpHeaders();
		headersJane.setBasicAuth("5", "AmSecondUser");
		UserReply addContactsReplyLoginFail = mapper.readValue(mvc.perform(post("/api/users/addcontact/").contentType("application/json").content(addContactBody).headers(headersJane)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!addContactsReplyLoginFail.isSuccess());
	}
	
	@Test
	void userServiceAddContactTests(@Autowired UserService userService) throws Exception{
		
		List<SiteUser> allUsers = userService.getUsers(null);
		assert(allUsers.size()==3);
		assert(allUsers.get(1).getId()==5);
		assert(allUsers.get(2).getId()==6);

		SiteUser tempJane = allUsers.get(1); 
		SiteUser tempFred = allUsers.get(2); 

		ArrayList<GrantedAuthority> janeAuthArray = new ArrayList<GrantedAuthority>();
		janeAuthArray.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
		UsernamePasswordAuthenticationToken janeLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, janeAuthArray ); 

		SiteUser newFredUserAddedContact = userService.addContact(tempFred, tempJane, janeLogin);
		assert(newFredUserAddedContact.getContactNum() == 1);
		assert(newFredUserAddedContact.getContacts()[0] == 5);
		
		allUsers = userService.getUsers(null);
		assert(allUsers.size()==3);
		tempJane = allUsers.get(1); 
		tempFred = allUsers.get(2); 
		assert(allUsers.get(2).getContactNum() == 1);
		assert(allUsers.get(2).getContacts()[0] == 5);
		
		ArrayList<GrantedAuthority> fredAuthArray = new ArrayList<GrantedAuthority>();
		fredAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken fredLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("6"), (Object) null, fredAuthArray ); 

		SiteUser newJaneUserAddedContact = userService.addContact(tempJane, tempFred, fredLogin);
		assert(newJaneUserAddedContact.getContactNum() == 1);
		assert(newJaneUserAddedContact.getContacts()[0] == 6);
		allUsers = userService.getUsers(null);
		assert(allUsers.size()==3);
		assert(allUsers.get(1).getContactNum() == 1);
		assert(allUsers.get(1).getContacts()[0] == 6);
		SiteUser tempBob = allUsers.get(0);
		
		ArrayList<GrantedAuthority> bobAuthArray = new ArrayList<GrantedAuthority>();
		bobAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken bobLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("4"), (Object) null, bobAuthArray ); 
		boolean errored = false;
		SiteUser addedContactFailSelfAdd;
		try{
			addedContactFailSelfAdd = userService.addContact(tempBob, tempBob, bobLogin);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
	}
	
	@Test
	void userControllerDeleteUserTests(@Autowired MockMvc mvc) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();

		assert(mvc.perform(delete("/api/users/4")).andReturn().getResponse().getStatus() == 401);
		// UserReply deleteUserReplyFailNoLogin = mapper.readValue(mvc.perform(delete("/api/users/4")).andReturn().getResponse().getContentAsString(), UserReply.class);
		// assert(!deleteUserReplyFailNoLogin.isSuccess());
		//Set up for this test is dependent on how spring is set up to process login information
		
		HttpHeaders headersFred = new HttpHeaders();
		headersFred.setBasicAuth("6", "AmThirdUser");
		UserReply deleteUserReplyFailWrongUserLogin = mapper.readValue(mvc.perform(delete("/api/users/4").headers(headersFred)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(!deleteUserReplyFailWrongUserLogin.isSuccess());
		
		HttpHeaders headersBob = new HttpHeaders();
		headersBob.setBasicAuth("4", "AmFirstUser");

		assert(mvc.perform(get("/api/users/all").headers(headersBob)).andReturn().getResponse().getStatus() == 200);
		assert(mvc.perform(get("/api/users/all").headers(headersFred)).andReturn().getResponse().getStatus() == 200);

		UserReply deleteUserReplyBob = mapper.readValue(mvc.perform(delete("/api/users/4").headers(headersBob)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(deleteUserReplyBob.isSuccess());

		UserReply allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==2);
		assert(allUserReply.getAllUsers().get(0).getId()==5);

		HttpHeaders headersJane = new HttpHeaders();
		headersJane.setBasicAuth("5", "AmSecondUser");
		UserReply deleteUserReplyFred = mapper.readValue(mvc.perform(delete("/api/users/6").headers(headersJane)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(deleteUserReplyFred.isSuccess());

		allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==1);
		assert(allUserReply.getAllUsers().get(0).getId()==5);
		
		assert(mvc.perform(get("/api/users/all").headers(headersBob)).andReturn().getResponse().getStatus() == 401);
		assert(mvc.perform(get("/api/users/all").headers(headersFred)).andReturn().getResponse().getStatus() == 401);

		UserReply deleteUserReplyJane = mapper.readValue(mvc.perform(delete("/api/users/5").headers(headersJane)).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(deleteUserReplyJane.isSuccess());

		allUserReply = mapper.readValue(mvc.perform(get("/api/users/all")).andReturn().getResponse().getContentAsString(), UserReply.class);
		assert(allUserReply.isSuccess());
		assert(allUserReply.getAllUsers().size()==0);
		assert(mvc.perform(get("/api/users/all").headers(headersJane)).andReturn().getResponse().getStatus() == 401);
	}

	
	@Test
	void userServiceDeleteUserTests(@Autowired UserService userService) throws Exception{

		boolean errored = false;
		String deletedUserIdFailNoLogin;
		try{
			deletedUserIdFailNoLogin = userService.deleteUser(Long.valueOf(4), null);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);

		ArrayList<GrantedAuthority> fredAuthArray = new ArrayList<GrantedAuthority>();
		fredAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken fredLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("6"), (Object) null, fredAuthArray ); 
		errored = false;
		String deletedUserIdFailWrongLogin;
		try{
			deletedUserIdFailWrongLogin = userService.deleteUser(Long.valueOf(4), fredLogin);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);

		ArrayList<GrantedAuthority> bobAuthArray = new ArrayList<GrantedAuthority>();
		bobAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken bobLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("4"), (Object) null, bobAuthArray ); 
		String deletedUserIdBob = userService.deleteUser(Long.valueOf(4), bobLogin);
		assert(deletedUserIdBob.equals("4"));
		assert(userService.getUsers(null).size() == 2);
		assert(userService.getUsers(null).get(0).getId() == 5);
		
		ArrayList<GrantedAuthority> janeAuthArray = new ArrayList<GrantedAuthority>();
		janeAuthArray.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
		UsernamePasswordAuthenticationToken janeLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("5"), (Object) null, janeAuthArray ); 
		String deletedUserIdFred = userService.deleteUser(Long.valueOf(6), janeLogin);
		assert(deletedUserIdFred.equals("6"));
		assert(userService.getUsers(null).size() == 1);
		assert(userService.getUsers(null).get(0).getId() == 5);
		
		String deletedUserIdJane = userService.deleteUser(Long.valueOf(5), janeLogin);
		assert(deletedUserIdJane.equals("5"));
		assert(userService.getUsers(null).size() == 0);
	}
	
	@Test
	void userControllerAttemptLoginTests(@Autowired MockMvc mvc) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();

		GetRoleReply attemptLoginReplyFailNoLogin = mapper.readValue(mvc.perform(get("/login/get_role/")).andReturn().getResponse().getContentAsString(), GetRoleReply.class);
		assert(!attemptLoginReplyFailNoLogin.isSuccess());

		HttpHeaders headersFred = new HttpHeaders();
		headersFred.setBasicAuth("6", "AmThirdUser");

		GetRoleReply attemptLoginReplyFred = mapper.readValue(mvc.perform(get("/login/get_role/").headers(headersFred)).andReturn().getResponse().getContentAsString(), GetRoleReply.class);
		assert(attemptLoginReplyFred.isSuccess());
		assert(!attemptLoginReplyFred.isAdmin());
		assert(attemptLoginReplyFred.getCurrentUser().getFirstName().equals("Fred"));

		HttpHeaders headersJane = new HttpHeaders();
		headersJane.setBasicAuth("5", "AmSecondUser");

		GetRoleReply attemptLoginReplyJane = mapper.readValue(mvc.perform(get("/login/get_role/").headers(headersJane)).andReturn().getResponse().getContentAsString(), GetRoleReply.class);
		assert(attemptLoginReplyJane.isSuccess());
		assert(attemptLoginReplyJane.isAdmin());
		assert(attemptLoginReplyJane.getCurrentUser().getFirstName().equals("Jane"));
	}
	
	@Test
	void userServiceAttemptLoginTests(@Autowired UserService userService) throws Exception{
		
		boolean errored = false;
		SiteUser currentUser;
		try{
			currentUser = userService.attemptLogin_GetRole(null);
		} catch (Exception e) {
			errored = true;
		}
		assert(errored);
		
		ArrayList<GrantedAuthority> bobAuthArray = new ArrayList<GrantedAuthority>();
		bobAuthArray.add( new SimpleGrantedAuthority("ROLE_USER") );
		UsernamePasswordAuthenticationToken bobLogin = new UsernamePasswordAuthenticationToken((Object) new UserPrincipal("4"), (Object) null, bobAuthArray ); 
		currentUser = userService.attemptLogin_GetRole(bobLogin);
		assert(currentUser.getFirstName().equals("Bob"));
	}

	
	@Test
	void userServiceCheckUserConsistancyTests(@Autowired UserService userService) throws Exception{
		SiteUser tempUser = new SiteUser(
			"Jessie_ ",
			"Kal- ",
			"(Spooky)",
			new Long[0],
			0,
			"I am a GGGhost!?",
			"Not actually a ghost (-__-) ",
			1,
			'G',
			"crimson",
			"teal",
			"gold",
			"red"
		);
		assert((boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setCardColor(tempUser.getCardColor()+" ");
		assert(! (boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setCardColor("teal");
		tempUser.setTitle(tempUser.getTitle()+"&");
		assert(! (boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setTitle("(Spooky)");
		tempUser.setSecret(tempUser.getSecret()+" $");
		assert(! (boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setSecret("Not actually a ghost (-__-) ");
		tempUser.setLastTheme(99);
		assert(! (boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setLastTheme(0);
		tempUser.setContactNum(99);
		assert(! (boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
		tempUser.setContactNum(0);
		assert((boolean) ReflectionTestUtils.invokeMethod(userService, "checkUserConsistancy", tempUser));
	}

	@Test
	void themesControllerAllTests(@Autowired MockMvc mvc, @Autowired ThemeService themeService) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();

		Themes[] gotAllThemes = mapper.readValue(mvc.perform(get("/api/themes/all/")).andReturn().getResponse().getContentAsString(), Themes[].class);
		assert(gotAllThemes.length == 3);
		assert(gotAllThemes[0].getActiveTabColor().equals("yellow"));
		assert(gotAllThemes[1].getBackgroundColor().equals("purple"));
		assert(gotAllThemes[2].getTextColor().equals("orange"));
		
		Themes tempTheme = new Themes(
			"yellow",
			"pink",
			"red",
			"gold",
			"orange",
			"navy",
			"blue",
			"green",
			"black",
			"indigo",

			"brown",
			"gold",
			"purple",
			"beige",
			"silver",
			"teal",
			"aqua",
			"olive"
		);
		themeService.addTheme(tempTheme);
		gotAllThemes = mapper.readValue(mvc.perform(get("/api/themes/all/")).andReturn().getResponse().getContentAsString(), Themes[].class);
		assert(gotAllThemes.length == 4);
		assert(gotAllThemes[3].getActiveTabColor().equals(tempTheme.getActiveTabColor()));
		assert(gotAllThemes[3].getBackgroundColor().equals(tempTheme.getBackgroundColor()));
		assert(gotAllThemes[3].getTextColor().equals(tempTheme.getTextColor()));
		tempTheme = gotAllThemes[3];

		tempTheme.setInactiveTabColor("gold");
		
		String updateThemes = mapper.writeValueAsString(tempTheme);
		HttpHeaders headersFred = new HttpHeaders();
		headersFred.setBasicAuth("6", "AmThirdUser");

		String updateThemeFred = mvc.perform(post("/api/themes/").headers(headersFred).contentType("application/json").content(updateThemes)).andReturn().getResponse().getContentAsString();
		// assert(updateThemeFred == null);
		assert(updateThemeFred.equals(""));

		HttpHeaders headersJane = new HttpHeaders();
		headersJane.setBasicAuth("5", "AmSecondUser");

		Themes updateThemeJane = mapper.readValue(mvc.perform(post("/api/themes/").headers(headersJane).contentType("application/json").content(updateThemes)).andReturn().getResponse().getContentAsString(), Themes.class);
		assert(updateThemeJane!=null);
		assert(updateThemeJane.getInactiveTabColor().equals("gold"));
		assert(updateThemeJane.getActiveTabColor().equals(tempTheme.getActiveTabColor()));
		assert(updateThemeJane.getBackgroundColor().equals(tempTheme.getBackgroundColor()));
		assert(updateThemeJane.getTextColor().equals(tempTheme.getTextColor()));
		
		gotAllThemes = mapper.readValue(mvc.perform(get("/api/themes/all/")).andReturn().getResponse().getContentAsString(), Themes[].class);
		assert(gotAllThemes.length == 4);
		assert(gotAllThemes[3].getInactiveTabColor().equals("gold"));
		assert(gotAllThemes[3].getActiveTabColor().equals(tempTheme.getActiveTabColor()));
		assert(gotAllThemes[3].getBackgroundColor().equals(tempTheme.getBackgroundColor()));
		assert(gotAllThemes[3].getTextColor().equals(tempTheme.getTextColor()));
	}
}
