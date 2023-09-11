package com.ssafy.enjoytrip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.model.dto.AttractionDto;

@Mapper
public interface AttractionLikeMapper {

	int getAttrLikeNum(int contentId);

	int getIsLiked(@Param("contentId") int contentId, @Param("id") String id);

	int likeAttr(@Param("contentId") int contentId, @Param("id") String id);

	int unLikeAttr(@Param("contentId") int contentId, @Param("id") String id);

	List<AttractionDto> getUserLikes(String userId);

}
