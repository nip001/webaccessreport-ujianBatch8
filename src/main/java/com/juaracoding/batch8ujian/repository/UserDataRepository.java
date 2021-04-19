package com.juaracoding.batch8ujian.repository;

import org.springframework.data.repository.CrudRepository;

import com.juaracoding.batch8ujian.entity.UserData;

public interface UserDataRepository  extends CrudRepository<UserData, Long>{

	public UserData findByUsername(String username);
}
