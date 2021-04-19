package com.juaracoding.batch8ujian.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.juaracoding.batch8ujian.entity.UserData;
import com.juaracoding.batch8ujian.repository.UserDataRepository;

public class CustomUserDetailServices implements UserDetailsService {

	@Autowired
	private UserDataRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserData user = userRepo.findByUsername(username);		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetail(user);
	}
}
