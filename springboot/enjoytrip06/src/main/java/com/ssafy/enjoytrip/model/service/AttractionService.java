package com.ssafy.enjoytrip.model.service;
import com.ssafy.enjoytrip.model.dto.SidoDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.AttractionMapper;
import com.ssafy.enjoytrip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.model.dto.GugunDto;

@Service
public class AttractionService {
	@Autowired
	private AttractionMapper mapper;
	
	//////// service /////////
	public List<AttractionDto> getAttractionInfos(int sidoCode, int gugunCode, int contentTypeId, String title){
		return mapper.select(sidoCode, gugunCode, contentTypeId, title);
	}
	public AttractionDto readAttractionDetail(int contentId) {
		return mapper.selectOne(contentId);
	}
	
	public List<SidoDto> getSidos() {
		return mapper.selectSidos();
	}
	
	public List<GugunDto> getGuguns(int sidoCode) {
		return mapper.selectGuguns(sidoCode);
	}
}