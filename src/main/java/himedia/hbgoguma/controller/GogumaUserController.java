package himedia.hbgoguma.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import himedia.hbgoguma.repository.vo.GogumaLoginData;
import himedia.hbgoguma.repository.vo.GogumaUser;
import himedia.hbgoguma.service.GogumaUserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/gogumauser")
public class GogumaUserController {
	@Autowired
	private GogumaUserService gogumaUserSerivce;
	
//	GET : /api/gogumauser
	@GetMapping
	public ResponseEntity<List<GogumaUser>> getAllUsers() {
		List<GogumaUser> items = gogumaUserSerivce.selectAllUsers();
		return ResponseEntity.ok(items);
	}
	
//	POST : /api/gogumauser/login -> 로그인
	@PostMapping("/login")
	public ResponseEntity<GogumaUser> loginUser(@RequestBody GogumaLoginData loginData, HttpSession session) {		
		//@note - 세션 정보가 있다면
		if(session != null && session.getAttribute("loginUser") != null) {
			GogumaUser loginUser = (GogumaUser)session.getAttribute("loginUser");
			return ResponseEntity.ok(loginUser);
		}
		
		//@note - 유저가 친 아이디나 비번이 없을 경우
		if (loginData.getUser_id().length() == 0 || loginData.getPassword().length() == 0) {
			System.err.println("no user_id or password");
			
			return ResponseEntity.ofNullable(null);
		}
		
		GogumaUser loginUser = gogumaUserSerivce.loginUser(loginData);
		
		//@note - 로그인 성공
		if (loginUser != null) {
			// @note - 비밀번호 정보 지움
			loginUser.setPassword("");
			
			//@note -  로그인 시 세션정보 생성
			session.setAttribute("loginUser", loginUser);
			return ResponseEntity.ok(loginUser);
		} else {
			return ResponseEntity.ofNullable(null);
		}
	}
	
	//@note - 세선 정보 소멸
	@PostMapping("/logout")
	public void logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
	}
	
//	POST : /api/gogumauser -> 새로운 유저 항목 생성
	@PostMapping
	public ResponseEntity<GogumaUser> createUser(@RequestBody GogumaUser user) {
		GogumaUser savedUser = gogumaUserSerivce.registerUser(user);
		return ResponseEntity.ok(savedUser);
		//	ResponseEntity.created로 하는 것이 의미상 더 나을 수도 있다.
	}
	
//	PUT : /api/gogumauser/{uid} -> 기존 유저 항목 수정
	@PutMapping("/{uid}")
	public ResponseEntity<GogumaUser> updateUser(@RequestBody GogumaUser user, @PathVariable Long uid) {
		user.setUid(uid);
		GogumaUser updatedUser = gogumaUserSerivce.updateUser(user);
		return ResponseEntity.ok(updatedUser);
	}
	
//	DELETE : /api/gogumauser/{uid} -> 기존 유저 항목 삭제
	@DeleteMapping("/{uid}")
	//	Body에 실어 보낼 내용이 없음 -> Void
	public ResponseEntity<Void> deleteUser(@PathVariable Long uid) {
		gogumaUserSerivce.deleteUser(uid);
		return ResponseEntity.ok().<Void>build();
	}
	
//	Session : /api/session -> 로그인 상태 확인
	@GetMapping("/session")
	public ResponseEntity<GogumaUser> getSessionUser(HttpSession session) {
		GogumaUser loginUser = (GogumaUser) session.getAttribute("loginUser");
		
		if (loginUser != null) {
			return ResponseEntity.ok(loginUser);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
}