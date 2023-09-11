package com.ssafy.enjoytrip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.model.service.AttractionLikeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/attraction/like")
public class AttractionLikeController {
	@Autowired
	private AttractionLikeService service;
	
	/** 특정 관광지의 좋아요 수 가져오기 */
	@GetMapping("/{contentId}")
	public int like(@PathVariable int contentId) {
		return service.getAttrLikeNum(contentId);
	}
	
	/** 특정 관광지에 특정 사용자가 좋아요 했는지 알아보기 */
	@GetMapping("/{contentId}/{id}")
	public boolean isLiked(@PathVariable("contentId") int contentId, @PathVariable("id") String id) {
		return service.getIsLiked(contentId, id);
	}
	
	/** 유저가 좋아요 누른 리스트 출력 */
	@GetMapping
	public List<AttractionDto> userLikes(@RequestParam("userId")String userId){
		List<AttractionDto> result = service.getUserLikes(userId);
		System.out.println("userLikes:" + result);
		return result;
	}
	
	/** 특정 관광지에 좋아요 누르기 */
	@PutMapping
	public boolean like(@RequestBody Map<String, Object>map) {
		int contentId = (int) map.get("contentId");
		String id = (String) map.get("id");
		return service.likeAttr(contentId, id);
	}
	
	/** 특정 유저가 특정 관광지의 좋아요 해제 */
	@DeleteMapping("/{contentId}/{id}")
	public boolean unlike(@PathVariable int contentId,  @PathVariable("id") String id) {
		return service.unLikeAttr(contentId, id);
	}
}
