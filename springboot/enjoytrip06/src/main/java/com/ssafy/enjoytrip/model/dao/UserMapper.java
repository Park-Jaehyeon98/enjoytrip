package com.ssafy.enjoytrip.model.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.UserDto;

@Mapper
public interface UserMapper {
	public UserDto select(@Param("id") String id, @Param("pw") String pw) throws SQLException;
	public UserDto getUserInfo(String id)  throws SQLException;
	public int update(@Param("id")String id, @Param("name")String name, @Param("email")String email, @Param("pw")String pw, @Param("newpw")String newpw)  throws SQLException;
	public int delete(String id) throws SQLException;
	public int insert(UserDto user)  throws SQLException;
	
	// jwt
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
}
