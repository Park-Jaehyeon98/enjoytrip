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

import com.ssafy.enjoytrip.model.dto.BoardCommentDto;
import com.ssafy.enjoytrip.model.service.BoardCommentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/board/comment")
public class BoardCommentController {
	@Autowired
	private BoardCommentService service;
	///////////////////////////////////////
	@GetMapping("/{bno}")
	public List<BoardCommentDto> getComments(@PathVariable int bno) throws SQLException{
		return service.getComments(bno);
	}
	@PostMapping
	public int regist(@RequestBody BoardCommentDto comment) throws SQLException {
		System.out.println("댓글 등록: "+comment);
		return service.registComment(comment);
	}
	@PutMapping
	public int update(@RequestBody Map<String, Object> param) throws SQLException {
		String content = (String) param.get("content");
		int bcno = (int) param.get("bcno");
		System.out.println(bcno+"번 글 삭제: " + content);
		return service.updateComment(content, bcno);
	}
	@DeleteMapping("/{bcno}")
	public int delete(@PathVariable int bcno) throws SQLException {
		return service.deleteComment(bcno);
	}
}
