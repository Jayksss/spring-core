package com.springcore.web.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String loginId; // 로그인 ID
    private final String password; // 비밀번호
    private final String userNm; // 사용자 이름
    private final Collection<? extends GrantedAuthority> authorities; // 권한 목록

    public CustomUserDetails(String loginId, String password, String userNm, Collection<? extends GrantedAuthority> authorities) {
        this.loginId = loginId;
        this.password = password;
        this.userNm = userNm;
        this.authorities = authorities;
    }

    public String getUserNm() { // 사용자 이름 반환 메서드
        return userNm;
    }

    public String getLoginId() { // 로그인 ID 반환 메서드
        return loginId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { // `getUsername()`은 Spring Security에서 사용되므로 loginId 반환
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
