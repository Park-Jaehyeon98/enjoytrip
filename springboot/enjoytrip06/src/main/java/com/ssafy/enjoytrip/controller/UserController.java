package com.ssafy.enjoytrip.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.AttractionCommentDto;
import com.ssafy.enjoytrip.model.dto.BoardCommentDto;
import com.ssafy.enjoytrip.model.dto.BoardDto;
import com.ssafy.enjoytrip.model.dto.UserDto;
import com.ssafy.enjoytrip.model.service.AttractionCommentService;
import com.ssafy.enjoytrip.model.service.BoardCommentService;
import com.ssafy.enjoytrip.model.service.BoardService;
import com.ssafy.enjoytrip.model.service.JwtService;
import com.ssafy.enjoytrip.model.service.UserService;

@RestController
//@Controller
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	// http status 전용 변수
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService service;
	
	@Autowired
	private BoardService bService;
	
	@Autowired
	private AttractionCommentService acService;
	
	@Autowired
	private BoardCommentService bcService;

//	@PostMapping("/login")
//	public UserDto login(@RequestBody UserDto user, HttpSession session) {
//		UserDto result = service.login(user.getId(), user.getPw());
//		if (result != null) {
//			session.setAttribute("loginInfo", result);
//		}
//		return result;
//	}
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto user) {
		// 로그인 api 결과
		Map<String, Object> resultMap = new HashMap<>();
		// 결과에 같이 보낼 http 상태
		HttpStatus status = null;

		try {
			UserDto loginUser = service.login(user.getId(), user.getPw());
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", user.getId());// "userid", "ssafy"
				String refreshToken = jwtService.createRefreshToken("userid", user.getId());// key, data
				service.saveRefreshToken(user.getId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}

		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		ResponseEntity<Map<String, Object>> entity = new ResponseEntity<Map<String, Object>>(resultMap, status);
		System.out.println("entity 출력 : " + entity);
		return entity;
	}

	@GetMapping("/mypage/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") String id, HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = service.userInfo(id);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/** 로그아웃 */
	@GetMapping("/logout/{id}")
	public ResponseEntity<?> removeToken(@PathVariable("id") String id) {
//		session.invalidate();
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			service.deleRefreshToken(id);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@PutMapping("/update")
	public void update(@RequestBody Map<String, String> map) throws SQLException {
		if (service.updateUser(map.get("id"), map.get("name"), map.get("email"), map.get("pw"), map.get("newPw"))) {
			return;
		}
	}

	@DeleteMapping("/delete/{id}")
	public void remove(@PathVariable("id") String id) throws SQLException {
		System.out.println("delete 실행");
		if (service.deleteUser(id)) {
			return;
		}
	}

	@PostMapping("/regist")
	public boolean regist(@RequestBody UserDto user) throws SQLException {
		return service.insertUser(user);
	}

	/** 액세스토큰 재발급 */
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, userDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(service.getRefreshToken(userDto.getId()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/mypage/myboard/{id}")
	public List<BoardDto> getMyBoard(@PathVariable("id") String id) throws SQLException {
		System.out.println("getMyBoard id:"+id);
		List<BoardDto> list = bService.getMyBoardList(id);
		System.out.println(list);
		return list;
	}

	@GetMapping("/mypage/myattractioncomment/{id}")
	public List<AttractionCommentDto> getMyAttractionComment(@PathVariable("id") String id) throws SQLException {
		List<AttractionCommentDto> list = acService.getMyAttractionComment(id);
		System.out.println(list);
		return list;
	}
	
	@GetMapping("/mypage/myboardcomment/{id}")
	public List<BoardCommentDto> getMyBoardComment(@PathVariable("id") String id) throws SQLException{
		List<BoardCommentDto> list = bcService.getMyBoardComment(id);
		System.out.println(list);
		return list;
	}
}
