package com.bridgelabz.fundoo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
@PropertySource("classpath:message.properties")

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private Jwt jwt;

	@Autowired
	private Jms jms;

	@Autowired
	private BCryptPasswordEncoder bcryptpasswordencoder;

	@Autowired
	private Environment environment;

	/**
	 * @purpose: to add user to the registration page
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
	 * @purpose: for login to the user.
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

	@Override
	public Response profilePic(String token, MultipartFile file) throws IOException {
		String email = jwt.getUserToken(token);
		User user = repository.findByemail(email);
		if (user != null) {
			if (file.getOriginalFilename().contains(".jpg") || file.getOriginalFilename().contains(".jpeg")
					|| file.getOriginalFilename().contains(".png")) {
				File convertFile = new File("/home/admin1/Desktop" + file.getOriginalFilename());
				convertFile.createNewFile();
				FileOutputStream fileOpStrm = new FileOutputStream(convertFile);
				String pic = "/home/admin1/Desktop" + file.getOriginalFilename();
				user.setProfilePic(pic);
				repository.save(user);
				fileOpStrm.write(file.getBytes());
				fileOpStrm.close();
				return new Response(200, environment.getProperty("PIC_UPLOAD"), HttpStatus.OK);
			}
			return new Response(400, environment.getProperty("INCORRECT_EXTENSION"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response deletePic(String token) {
		String email = jwt.getUserToken(token);
		User user = repository.findByemail(email);
		if (user != null) {
			user.setProfilePic(null);
			repository.save(user);
			return new Response(200, environment.getProperty("PIC_DELETED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response editPic(String token, MultipartFile file) throws IOException {
		String email = jwt.getUserToken(token);
		User user = repository.findByemail(email);
		if (user != null) {
			String previousProfilePic = user.getProfilePic();
			if (previousProfilePic != null) {
				user.setProfilePic(null);
				if (file.getOriginalFilename().contains(".jpg") || file.getOriginalFilename().contains(".jpeg")
						|| file.getOriginalFilename().contains(".png")) {
					File convertFile = new File("/home/admin1/Desktop" + file.getOriginalFilename());
					convertFile.createNewFile();
					FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
					String pic = "/home/admin1/Desktop" + file.getOriginalFilename();
					user.setProfilePic(pic);
					repository.save(user);
					fileOutputStream.write(file.getBytes());
					fileOutputStream.close();
					return new Response(200, environment.getProperty("EDIT_PROFILE_PIC"), HttpStatus.OK);
				}
				return new Response(400, environment.getProperty("INCORRECT_EXTENSION"), HttpStatus.BAD_REQUEST);
			}
			return new Response(400, environment.getProperty("PROFILE_PIC_NOT_EXISTS"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

}
