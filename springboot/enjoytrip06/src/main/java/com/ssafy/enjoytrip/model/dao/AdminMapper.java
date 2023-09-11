package com.ssafy.enjoytrip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.UserDto;

@Mapper
public interface AdminMapper {
	public List<UserDto> selectAll();
	public int delete(String id);
}
