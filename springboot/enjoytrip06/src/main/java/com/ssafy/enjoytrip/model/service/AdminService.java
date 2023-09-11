package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.AdminMapper;
import com.ssafy.enjoytrip.model.dto.UserDto;

@Service
public class AdminService {
	@Autowired
	private AdminMapper mapper;
	
	public List<UserDto> getUsers(){
		return mapper.selectAll();
	}
	
	public boolean removeUser(String id) {
		if(mapper.delete(id)>0)
			return true;
		return false;
	}
}
