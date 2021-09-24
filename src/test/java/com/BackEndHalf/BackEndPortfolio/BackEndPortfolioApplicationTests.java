package com.BackEndHalf.BackEndPortfolio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackEndPortfolioApplicationTests {

	@Test
	void contextLoads(@Autowired MockMvc mvc) throws Exception{
		mvc.perform(get("/api/users/all")).andExpect(status().isOk()).andExpect(content().string("Hello World"));
	}

	@Test
	void contextLoads2(@Autowired UserService userService) {
		assert(userService.getUsers(null) != null);
	}

	// @Test
	// void contextLoads2() {
	// 	UserService userService = new UserService(new UserRepository(){
			
	// 	}, null, null)
	// 	assert(userService.getUsers(null) != null);
	// }
	
}
