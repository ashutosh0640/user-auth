package com.backendbyte.userauth.service;

import com.backendbyte.userauth.dto.LoginRequestDTO;

public interface LoginService {
	
	public String verifyLogin(LoginRequestDTO loginDto) ;

}
