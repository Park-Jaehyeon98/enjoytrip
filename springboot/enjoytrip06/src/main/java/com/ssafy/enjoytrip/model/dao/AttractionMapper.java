package com.ssafy.enjoytrip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.model.dto.GugunDto;
import com.ssafy.enjoytrip.model.dto.SidoDto;

@Mapper
public interface AttractionMapper {
	public List<AttractionDto> select(@Param("sidoCode") int sidoCode, @Param("gugunCode") int gugunCode, @Param("contentTypeId")int contentTypeId, @Param("title") String title);
	public AttractionDto selectOne(int contentId);
	public List<SidoDto> selectSidos();
	public List<GugunDto> selectGuguns(int sidoCode);
}
