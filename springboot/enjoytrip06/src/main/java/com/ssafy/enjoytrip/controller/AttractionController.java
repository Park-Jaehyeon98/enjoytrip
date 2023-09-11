package com.ssafy.enjoytrip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.model.dto.GugunDto;
import com.ssafy.enjoytrip.model.dto.SidoDto;
import com.ssafy.enjoytrip.model.service.AttractionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/attraction")
public class AttractionController {
	@Autowired
	private AttractionService service;
	
	@GetMapping
	public List<AttractionDto> list(@RequestParam("sidoCode") int sidoCode, @RequestParam("gugunCode") int gugunCode, @RequestParam("contentTypeId")int contentTypeId, @RequestParam("title")String title){
//		public List<AttractionDto> list(@RequestParam("searchObj") AttractionDto searchObj){
//		System.out.println("att  : "+attraction);
		List<AttractionDto> li =service.getAttractionInfos(sidoCode, gugunCode, contentTypeId, title);
//		System.out.println(li);
		return li;
//		int sidoCode = searchObj.getSidoCode();
//		int gugunCode = searchObj.getGugunCode();
//		int contentTypeId = searchObj.getContentTypeId();
//		String title = searchObj.getTitle();
//		return service.getAttractionInfos(sidoCode, gugunCode, contentTypeId, title);
		
	}
	
	@GetMapping("/{contentId}")
	public AttractionDto read(@PathVariable("contentId") int contentId) {
		AttractionDto result =service.readAttractionDetail(contentId);
		System.out.println(result);
		return result;
	}
	
	@GetMapping("/sido")
	public List<SidoDto> getSido() {
		System.out.println("call sido list");
		return service.getSidos();
	}
	
	@GetMapping("/sido/{sidoCode}")
	public List<GugunDto> getGugun(@PathVariable("sidoCode") int sidoCode){
		return service.getGuguns(sidoCode);
	}
}
