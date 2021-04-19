package com.juaracoding.batch8ujian.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juaracoding.batch8ujian.entity.UserData;
import com.juaracoding.batch8ujian.repository.UserDataRepository;

@Service
public class ModelUserData implements UserDataInterface{
	@Autowired
	UserDataRepository userDataRepo;
	
	@Override
	public UserData addUser(UserData user) {
		// TODO Auto-generated method stub
		return userDataRepo.save(user);
	}

}
