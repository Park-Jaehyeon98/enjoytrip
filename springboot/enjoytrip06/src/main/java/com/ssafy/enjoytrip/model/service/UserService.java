package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.UserMapper;
import com.ssafy.enjoytrip.model.dto.UserDto;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;

	public UserDto login(String id, String pw) throws SQLException {
		return mapper.select(id, pw);
	}

	public boolean updateUser(String id, String name, String email, String pw, String newpw) throws SQLException {
		if (mapper.update(id, name, email, pw, newpw) > 0)
			return true;
		return false;
	}
	
	public boolean deleteUser(String id) throws SQLException{
		if(mapper.delete(id)>0)
			return true;
		return false;
	}

	public boolean insertUser(UserDto user) throws SQLException{
		if(mapper.insert(user)>0)
			return true;
		return false;
	}
	
	public UserDto userInfo(String id) throws SQLException{
		return mapper.getUserInfo(id);
	}
	
	////////// jwt
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
//		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
		mapper.saveRefreshToken(map);
	}
	
	public Object getRefreshToken(String userid) throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).getRefreshToken(userid);
		return mapper.getRefreshToken(userid);
	}
	
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", id);
		map.put("token", null);
//		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
		mapper.deleteRefreshToken(map);
	}

}
