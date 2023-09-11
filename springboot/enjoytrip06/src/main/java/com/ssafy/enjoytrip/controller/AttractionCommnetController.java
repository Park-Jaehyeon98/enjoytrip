package com.ssafy.enjoytrip.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.AttractionCommentDto;
import com.ssafy.enjoytrip.model.service.AttractionCommentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/attraction/comment")
public class AttractionCommnetController {
	@Autowired
	private AttractionCommentService service;
	//////////////////////////////////////////
	@GetMapping("/{contentId}")
	public List<AttractionCommentDto> getComments(@PathVariable int contentId) throws SQLException{
		return service.getComments(contentId);
	}
	@PostMapping
	public int regist(@RequestBody AttractionCommentDto comment) throws SQLException {
		System.out.println("댓글 등록: "+comment);
		return service.registComment(comment);
	}
	@PutMapping
	public int update(@RequestBody Map<String, Object> param) throws SQLException {
		String content = (String) param.get("content");
		int acno = (int) param.get("acno");
		System.out.println(acno+"번 글 삭제: " + content);
		return service.updateComment(content, acno);
	}
	@DeleteMapping("/{acno}")
	public int delete(@PathVariable int acno) throws SQLException {
		return service.deleteComment(acno);
	}
}
