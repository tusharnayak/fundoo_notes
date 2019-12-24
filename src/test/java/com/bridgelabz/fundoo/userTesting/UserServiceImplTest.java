package com.bridgelabz.fundoo.userTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.services.UserServiceImpl;
import com.bridgelabz.fundoo.utility.Jms;
import com.bridgelabz.fundoo.utility.Jwt;

class UserServiceImplTest {
	@Mock
	private UserRepository repository;

	@Mock
	private Jwt jwt;

	@Mock
	private Jms jms;

	@Mock
	private BCryptPasswordEncoder bcryptpasswordencoder;

	String email = "nayak.tushar1996@gmail.com";
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5heWFrLnR1c2hhcjE5OTZAZ21haWwuY29tIn0.RY_G8zFIBZf12ysBahWUbhMb3fETAF8tkMNVAGLlD6o";
	String password = "suzuki@123";
	String confirmPassword = "suzuki@123";

	ModelMapper mapper = new ModelMapper();
	RegistrationDto registrationdto = new RegistrationDto();
	User user = new User();
	UserServiceImpl userserviceImpl=new UserServiceImpl();

	@Test
	void testAddUser() {

		when(mapper.map(registrationdto, User.class)).thenReturn(user);
		when(registrationdto.getPassword()).thenReturn(password);
		when(registrationdto.getConfirmPassword()).thenReturn(confirmPassword);
		//when(bcryptpasswordencoder.encode("password")).thenReturn(toString());
		when(jwt.createToken(email)).thenReturn(token);
		jms.sendMail(email, token);
		Response response=userserviceImpl.addUser(registrationdto);
		assertEquals(200, response.getStatus());
		
	}

	@Test
	void testLoginUser() {

	}

	@Test
	void testForgetPassword() {

	}

	@Test
	void testResetPassword() {

	}

}
