package com.springcore.web.main.controller;

import com.springcore.web.main.service.MainService;
import com.springcore.web.user.service.CustomUserDetails;
import com.springcore.web.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private MainService mainService;

	@Autowired
	private UserService userService;

	@GetMapping("/main")
	public String main(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			model.addAttribute("loginId", userDetails.getLoginId()); // 로그인 ID
			model.addAttribute("userNm", userDetails.getUserNm()); // 사용자 이름
		} else {
			model.addAttribute("loginId", "Guest");
			model.addAttribute("userNm", "방문자");
		}

		model.addAttribute("list", mainService.selectMainInfo());
		
		return "content/main";
	}

	@GetMapping("/login")
	public String login(Model model) {
		//userService.updateAllPasswords();

		return "content/login";
	}

	@PostMapping("/login")
	public String login() {
		return "redirect:/main";  // Spring Security에서 처리하므로 보통 필요 없음
	}
}
