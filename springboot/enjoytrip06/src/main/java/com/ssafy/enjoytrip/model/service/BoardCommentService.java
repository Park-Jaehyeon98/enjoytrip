package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.BoardCommentMapper;
import com.ssafy.enjoytrip.model.dto.BoardCommentDto;

@Service
public class BoardCommentService {
	@Autowired
	private BoardCommentMapper dao;
	//////////////////////////////
	public List<BoardCommentDto> getComments(int bno) throws SQLException {
		return dao.selectAll(bno);
	}
	public int registComment(BoardCommentDto comment) throws SQLException {
		return dao.insert(comment);
	}
	
	public int updateComment(String content, int bcno) throws SQLException {
		return dao.update(content, bcno);
	}
	
	public int deleteComment(int bcno) throws SQLException {
		return dao.delete(bcno);
	}
	public List<BoardCommentDto> getMyBoardComment(String id) throws SQLException{
		return dao.myBoardComment(id);
	}
}
