package com.springcore.web.user.service;

import com.springcore.web.user.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Map<String, Object> user = userMapper.findUserByLoginId(loginId);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + loginId);
        }

        return new CustomUserDetails(
                (String) user.get("LOGIN_ID"), // 로그인 ID
                (String) user.get("PWD_HASH"), // 암호화된 비밀번호
                (String) user.get("USER_NM"), // 사용자 이름
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 기본 권한
        );
    }
}
