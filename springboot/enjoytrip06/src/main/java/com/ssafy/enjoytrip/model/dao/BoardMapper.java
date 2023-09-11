package com.ssafy.enjoytrip.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.BoardDto;

@Mapper
public interface BoardMapper {
	int insert(BoardDto b) throws SQLException;
	List<BoardDto> selectAll() throws SQLException;	
	BoardDto selectOne(int bno) throws SQLException;
	int delete(int bno) throws SQLException;
	int update(@Param("bno")int bno, @Param("subject") String subject, @Param("content") String content) throws SQLException;
	List<BoardDto> titleSearch(String keyword) throws SQLException;
	List<BoardDto> contentSearch(String keyword) throws SQLException;
	List<BoardDto> writerSearch(String keyword) throws SQLException;
	void hitadd(int bno) throws SQLException;
	List<BoardDto> myBoardList(String id) throws SQLException;
}