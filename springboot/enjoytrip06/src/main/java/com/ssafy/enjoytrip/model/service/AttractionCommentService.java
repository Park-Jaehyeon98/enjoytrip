package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.AttractionCommentMapper;
import com.ssafy.enjoytrip.model.dto.AttractionCommentDto;

@Service
public class AttractionCommentService {
	@Autowired
	private AttractionCommentMapper dao;
	////////////////////////////////////////
	public List<AttractionCommentDto> getComments(int contentId) throws SQLException {
		return dao.selectAll(contentId);
	}
	
	public int registComment(AttractionCommentDto comment) throws SQLException {
		return dao.insert(comment);
	}
	
	public int updateComment(String content, int acno) throws SQLException {
		return dao.update(content, acno);
	}
	
	public int deleteComment(int acno) throws SQLException {
		return dao.delete(acno);
	}
	
	public List<AttractionCommentDto> getMyAttractionComment(String id) throws SQLException {
		return dao.myAttractionComment(id);
	}
}
