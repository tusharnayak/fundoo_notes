package com.bridgelabz.fundoo.services;


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
	
	//public List<User> getUser(int id);
}
