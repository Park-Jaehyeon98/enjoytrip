package com.ssafy.enjoytrip.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.BoardCommentDto;

@Mapper
public interface BoardCommentMapper {
	List<BoardCommentDto> selectAll(int bno) throws SQLException;
	int insert(BoardCommentDto comment) throws SQLException;
	int update(@Param("content")String content, @Param("bcno")int bcno) throws SQLException;
	int delete(int bcno) throws SQLException;
	List<BoardCommentDto> myBoardComment(String id) throws SQLException;
} 
