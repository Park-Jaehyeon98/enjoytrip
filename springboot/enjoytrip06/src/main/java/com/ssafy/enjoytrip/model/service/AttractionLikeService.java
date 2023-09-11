package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dao.AttractionLikeMapper;
import com.ssafy.enjoytrip.model.dto.AttractionDto;

@Service
public class AttractionLikeService {
	@Autowired
	private AttractionLikeMapper mapper;
	
	/** 특정 관광지의 좋아요 수 가져오기 */
	public int getAttrLikeNum(int contentId) {
		int result = mapper.getAttrLikeNum(contentId);
		System.out.println("getAttrLikeNum : "+result);
		return result;
	}
	
	/** 특정 관광지에 특정 사용자가 좋아요 했는지 알아보기 */
	public boolean getIsLiked(int contentId, String id) {
		int result = mapper.getIsLiked(contentId, id);
		System.out.println("getIsLiked : "+result);
		if(result>0)
			return true;
		return false;
	}
	
	/** 특정 관광지에 좋아요 누르기 */
	public boolean likeAttr(int contentId, String id) {
		if(mapper.likeAttr(contentId, id)>0)
			return true;
		return false;
	}
	
	/** 특정 유저가 특정 관광지의 좋아요 해제 */
	public boolean unLikeAttr(int contentId, String id) {
		if(mapper.unLikeAttr(contentId, id)>0)
			return true;
		return false;

	}
	
	/** 유저의 좋아요 목록 */
	public List<AttractionDto> getUserLikes(String userId) {
		return mapper.getUserLikes(userId);
	}

}
