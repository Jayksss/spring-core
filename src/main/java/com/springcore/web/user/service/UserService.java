package com.springcore.web.user.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void updateAllPasswords() {
        String encodedPassword = passwordEncoder.encode("qwer1234"); // `qwer1234`를 `BCrypt`로 암호화
        String sql = "UPDATE spring_dev.USERS SET PWD_HASH = ?";
        jdbcTemplate.update(sql, encodedPassword);
        System.out.println("✅ 모든 사용자의 비밀번호가 안전하게 변경되었습니다.");
    }
}
