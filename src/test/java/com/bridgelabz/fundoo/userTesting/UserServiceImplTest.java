package com.bridgelabz.fundoo.userTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.omg.CORBA.Environment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgelabz.fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.services.UserServiceImpl;
import com.bridgelabz.fundoo.utility.Jms;
import com.bridgelabz.fundoo.utility.Jwt;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:message.properties")
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository repository;

	@Mock
	private Jwt jwt;

	@Mock
	private Jms jms;

	@Mock
	Environment environment;

	@Mock
	private BCryptPasswordEncoder bcryptpasswordencoder;

	String email = "nayak.tushar1996@gmail.com";
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5heWFrLnR1c2hhcjE5OTZAZ21haWwuY29tIn0.RY_G8zFIBZf12ysBahWUbhMb3fETAF8tkMNVAGLlD6o";
	String password = "suzuki@123";
	String confirmPassword = "suzuki@123";

	@Mock
	ModelMapper mapper;

	@Mock
	RegistrationDto registrationdto;
	
	public boolean status=false;

	@Mock
	LoginDto loginDto;
	@Mock
	ForgetPasswordDto forgetPassword;

	User user = new User();
	Optional<User> optionaluser = Optional.of(user);

	// @Test
	void testAddUser() {
		registrationdto.setFirstName("tushar");
		registrationdto.setLastName("nayak");
		registrationdto.setEmail(email);
		registrationdto.setUserName("tushar@123");
		registrationdto.setPassword(password);
		registrationdto.setConfirmPassword(confirmPassword);
		when(mapper.map(registrationdto, User.class)).thenReturn(user);
		when(jwt.createToken(email)).thenReturn(token);
		jms.sendMail(email, token);
		when(registrationdto.getPassword()).thenReturn(password);
		when(registrationdto.getConfirmPassword()).thenReturn(confirmPassword);
		// when(registrationdto.getPassword().equals(registrationdto.getConfirmPassword())).thenReturn(true);
		when(bcryptpasswordencoder.encode("password")).thenReturn(anyString());
		when(repository.save(user)).thenReturn(user);
		Response response = userServiceImpl.addUser(registrationdto);
		assertEquals(200, response.getStatus());

	}

	// @Test
	void testLoginUser() {
		loginDto.setEmail(email);
		loginDto.setPassword(confirmPassword);
		when(jwt.getUserToken(token)).thenReturn(email);
		when(mapper.map(loginDto, User.class)).thenReturn(user);
		// when(loginDto.getEmail().equals(email)).thenReturn(true);
		// when(loginDto.getPassword().equals(user.getConfirmPassword())).thenReturn(true);
		//when(bcryptpasswordencoder.encode("password")).thenReturn(anyString());
		when(repository.findByemail(email)).thenReturn(user);
		when(bcryptpasswordencoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(status);
		Response response = userServiceImpl.loginUser(loginDto, token);
		assertEquals(200, response.getStatus());
	}

	@Test
	void testForgetPassword() {
		
	}

	// @Test
	void testResetPassword() {

	}

}











