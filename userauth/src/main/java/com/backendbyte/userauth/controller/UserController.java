package com.backendbyte.userauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/test")
	@Tag(name = "User Controller", description = "APIs for user management")
	public String test() {
		return "This is user controller";
	}

}
