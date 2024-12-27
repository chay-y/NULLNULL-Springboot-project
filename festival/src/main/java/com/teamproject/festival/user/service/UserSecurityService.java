package com.teamproject.festival.user.service;

import com.teamproject.festival.user.constant.Role;
import com.teamproject.festival.user.dto.UserDto;
import com.teamproject.festival.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserMapper userMapper;

    // 이름으로 찾음
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userMapper.loginUser(username);

        if(user == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("ADMIN".equals(user.getUserRole().toString())){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));
        }

        System.out.println(user.getPassword());

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getUserRole().toString())
                .build();
    }
}
