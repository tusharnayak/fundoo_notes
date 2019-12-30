package com.bridgelabz.fundoo.services;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


import com.bridgelabz.fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.response.Response;


public interface UserService {
	
	public Response addUser(RegistrationDto registrationdto);
	
	public Response loginUser(LoginDto logindto,String token);
	
	public Response forgetPassword(ForgetPasswordDto forgetpassword );
	
	public Response resetPassword(ResetPasswordDto resetPassword,String token);
	
	public Response profilePic(String token, MultipartFile file) throws IOException;
	
	public Response deletePic(String token);
	
	public Response editPic(String token,MultipartFile file) throws IOException;
}
