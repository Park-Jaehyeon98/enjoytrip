package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.BoardMapper;
import com.ssafy.enjoytrip.model.dto.BoardDto;

@Service
public class BoardService {
	@Autowired
	private BoardMapper dao;

	public int add(BoardDto b) throws SQLException {
		return dao.insert(b);
	}

	public List<BoardDto> getBoards() throws SQLException {
		return dao.selectAll();
	}

	public BoardDto getBoard(int bno) throws SQLException {
		return dao.selectOne(bno);
	}

	public int remove(int bno) throws SQLException {
		return dao.delete(bno);
	}

	public int updateBoard(int bno, String subject, String content) throws SQLException {
		return dao.update(bno, subject, content);
	}

	public List<BoardDto> boardSearch(String condition, String keyword) throws SQLException {
		if ("제목".equals(condition)) {
			System.out.println("제목 실행");
			return dao.titleSearch(keyword);
		} else if ("작성자".equals(condition)) {
			System.out.println("작성자 실행");
			return dao.writerSearch(keyword);
		} else {
			System.out.println("제목+내용 실행");
			return dao.contentSearch(keyword);
		}
	}

	public void hitupdate(int bno) throws SQLException {
		dao.hitadd(bno);
		
	}

	public List<BoardDto> getMyBoardList(String id) throws SQLException {
		return dao.myBoardList(id);
	}
}
