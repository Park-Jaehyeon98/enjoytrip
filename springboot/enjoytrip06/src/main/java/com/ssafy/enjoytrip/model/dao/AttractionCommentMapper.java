package com.ssafy.enjoytrip.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.AttractionCommentDto;

@Mapper
public interface AttractionCommentMapper {
	List<AttractionCommentDto> selectAll(int contentId) throws SQLException;
	int insert(AttractionCommentDto comment) throws SQLException;
	int update(@Param("content")String content, @Param("acno")int acno) throws SQLException;
	int delete(int acno) throws SQLException;
	List<AttractionCommentDto> myAttractionComment(String id);
}
