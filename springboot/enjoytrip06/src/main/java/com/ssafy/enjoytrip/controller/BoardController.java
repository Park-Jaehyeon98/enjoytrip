package com.ssafy.enjoytrip.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.ssafy.enjoytrip.model.dto.BoardDto;
import com.ssafy.enjoytrip.model.dto.UserDto;
import com.ssafy.enjoytrip.model.service.BoardService;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService service;

//	게시물 리스트
	@GetMapping
	public List<BoardDto> getBoards() throws SQLException {
		return service.getBoards();
	}
	// 게시물 상세보기
	@GetMapping("/{bno}")
	public BoardDto getBoard(@PathVariable int bno) throws SQLException {
		service.hitupdate(bno);
		return service.getBoard(bno);
	}

	// 게시물 작성하기
	@PostMapping("/write")
	public int addBoard(@RequestBody BoardDto b) throws SQLException {
		return service.add(b);
	}
	

	// 게시물 삭제하기
	@DeleteMapping("/{bno}")
	public int removeBoard(@PathVariable int bno) throws SQLException { // localhost:7777/bookcafe/book?bno=17
		return service.remove(bno);
	}
	
	// 게시물 수정
	@PutMapping
	public int update(@RequestBody Map<String, String> map) throws SQLException {
//		int bno = (int) session.getAttribute("bno");
		int bno = Integer.parseInt(map.get("bno"));
		return service.updateBoard(bno, map.get("subject"), map.get("content"));	
	}
	
	// 게시글 검색
	@GetMapping("/search")
	public List<BoardDto> search(@RequestParam("condition") String condition, @RequestParam("keyword") String keyword) throws SQLException {
		List<BoardDto> list = service.boardSearch(condition, keyword);
		System.out.println(list);
		return list;
	}
	
	
}
