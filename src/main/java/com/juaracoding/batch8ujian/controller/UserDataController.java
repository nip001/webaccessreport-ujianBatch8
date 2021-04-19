package com.juaracoding.batch8ujian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.juaracoding.batch8ujian.entity.UserData;
import com.juaracoding.batch8ujian.services.ModelUserData;

@Controller
public class UserDataController {
	@Autowired
	ModelUserData modUser;
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		
		
		return "login";
		
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		
		model.addAttribute("userBaru", new UserData());
		return "registrasi";
		
	}
	
	@PostMapping("/register")
	public String registerPostPage(@ModelAttribute UserData dataUser, Model model) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String plainPassword = dataUser.getPassword();
		String encodedPassword = passwordEncoder.encode(plainPassword);
		dataUser.setPassword(encodedPassword);		
		
		this.modUser.addUser(dataUser);
		
		
		return "redirect:/login";
		
	}
}
