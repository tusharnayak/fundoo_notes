package com.bridgelabz.fundoo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.Jms;
import com.bridgelabz.fundoo.utility.Jwt;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private Jwt jwt;

	@Autowired
	private Jms jms;

	@Autowired
	private BCryptPasswordEncoder bcryptpasswordencoder;

/**
 *@purpose: to add user to the registration page
 */
@Override
public Response addUser(RegistrationDto registrationdto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(registrationdto, User.class);
		String token = jwt.createToken("tusharnayak1996@gmail.com");
		jms.sendMail("tusharnayak1996@gmail.com", token);
		String regdPass = registrationdto.getPassword();
		String confirmPass = registrationdto.getConfirmPassword();
		if (regdPass.equals(confirmPass)) {
			user.setPassword(bcryptpasswordencoder.encode(regdPass));
			repository.save(user);
			return new Response(200, "user added successfully", true);
		}
		return new Response(400, "check your password field", false);

	}

	/**
	 *@purpose: for login to the user.
	 */
	@Override
	public Response loginUser(LoginDto logindto, String token) {
		String email = jwt.getUserToken(token);
		if (email != null) {
			ModelMapper mapper = new ModelMapper();
			User user = mapper.map(logindto, User.class);
			if (email.equals(logindto.getEmail())) {
				System.out.println(email);
				boolean isValid = logindto.getPassword().equals(user.getConfirmPassword());
				System.out.println(isValid);
				if (isValid) {
					bcryptpasswordencoder.encode(logindto.getPassword());
					repository.save(user);
					return new Response(200, "login successfully", true);
				}
			}
		}
		return new Response(400, "invalid credential", false);
	}

	@Override
	public Response forgetPassword(ForgetPasswordDto forgetpassword) {
		User email = repository.findByemail(forgetpassword.getEmail());
		System.out.println(email);
		String token = jwt.createToken(forgetpassword.getEmail());
		System.out.println(token);
			if (email != null) {
				System.out.println("in email");
				jms.sendMail(forgetpassword.getEmail(), token);
				return new Response(200, "token sent to your mail ", true);
		}
		return new Response(400, "incorrect mail id", false);

	}

	@Override
	public Response resetPassword(ResetPasswordDto resetPassworddto, String token) {
		String resetPassToken = jwt.getUserToken(token);
		User user = repository.findByemail(resetPassToken);

		if (user != null) {
			String newPass = resetPassworddto.getNewPassword();
			String confirmPass = resetPassworddto.getConfirmPassword();

			if (newPass.equals(confirmPass)) {
				user.setPassword(bcryptpasswordencoder.encode(resetPassworddto.getNewPassword()));
				user.setConfirmPassword(resetPassworddto.getConfirmPassword());
				repository.save(user);
				return new Response(200, "password reset successfully", true);
			}
		}
		return new Response(400, "password is not matched", false);

	}
}
