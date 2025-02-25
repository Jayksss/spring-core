package com.springcore.web.main.controller;

import com.springcore.web.main.service.MainService;
import com.springcore.web.user.service.CustomUserDetails;
import com.springcore.web.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
	
	//log 선언
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MainService mainService;

	@Autowired
	private UserService userService;

	@GetMapping("/index")
	public String index(Model model) {
		
		logger.info("MethodName ::: {} ::: Start", Thread.currentThread().getStackTrace()[1].getMethodName());

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
		
		logger.info("MethodName ::: {} ::: End", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return "content/index";
	}

	@GetMapping("/main")
	public String main(Model model) {
		
		logger.info("MethodName ::: {} ::: Start", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date) + " >>> 메인페이지 연결확인");
		
		System.out.println(formatter.format(date) + " >>> 테스트쿼리");
		List<Map<String, Object>> list = mainService.selectMainInfo();
		logger.info("결과 >>> {}", list);
		
		model.addAttribute("list", list);
		
		logger.info("MethodName ::: {} ::: End", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return "content/main";
	}

	@GetMapping("/login")
	public String login(Model model) {

		logger.info("MethodName ::: {} ::: Start", Thread.currentThread().getStackTrace()[1].getMethodName());

		//userService.updateAllPasswords();

		logger.info("MethodName ::: {} ::: End", Thread.currentThread().getStackTrace()[1].getMethodName());

		return "content/login";
	}

	@PostMapping("/login")
	public String login() {
		logger.info("MethodName ::: {} ::: Start", Thread.currentThread().getStackTrace()[1].getMethodName());

		logger.info("MethodName ::: {} ::: End", Thread.currentThread().getStackTrace()[1].getMethodName());

		return "redirect:/index";  // Spring Security에서 처리하므로 보통 필요 없음
	}
}
