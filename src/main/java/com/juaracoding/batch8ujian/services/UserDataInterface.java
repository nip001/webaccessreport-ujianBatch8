package com.juaracoding.batch8ujian.services;

import com.juaracoding.batch8ujian.entity.UserData;

public interface UserDataInterface {
	public UserData addUser(UserData user);
	public UserData getUserByID(Long id);
}
