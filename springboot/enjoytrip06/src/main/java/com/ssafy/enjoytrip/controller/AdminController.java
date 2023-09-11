package com.ssafy.enjoytrip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.UserDto;
import com.ssafy.enjoytrip.model.service.AdminService;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService service;
	private final String AUTH_MSG = "lalaququpurin";
	
	// 유저 리스트 출력
	@GetMapping("/{authMsg}")
	public List<UserDto> list(@PathVariable("authMsg")String authMsg){
		if(authMsg.equals(AUTH_MSG))
			return service.getUsers();
		return null;
	}
	
	// 유저 삭제
	@DeleteMapping("/{authMsg}/{id}")
	public boolean delete(@PathVariable("authMsg")String authMsg, @PathVariable("id")String id) {
		if(authMsg.equals(AUTH_MSG))
			return service.removeUser(id);
		return false;
	}
}
