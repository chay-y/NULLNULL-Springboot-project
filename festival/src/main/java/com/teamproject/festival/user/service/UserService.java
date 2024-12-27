package com.teamproject.festival.user.service;

import com.teamproject.festival.user.dto.UserDto;
import com.teamproject.festival.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
    public int insertUser(UserDto userDto){

        this.overlapId(userDto.getUserId());
        this.overlapEmail(userDto.getUserEmail());

        // 비밀번호 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(encodedPassword);

        return userMapper.insertUser(userDto);
    }

    // id 중복 체크
    public void overlapId(String userId){
        UserDto findId = userMapper.overlapId(userId);

        if(findId != null){
            // IllegalStateException : 대상 객체의 상태가 호출된 메서드를 수행하기에
            // 적절하지 않을 때 발생시키는 예외
            throw new IllegalStateException("중복된 아이디입니다.");
        }
    }

    // 이메일 중복 체크
    public void overlapEmail(String email){
        UserDto findEmail = userMapper.overlapEmail(email);

        if(findEmail != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }

    // 로그인
    public UserDto login(String userId, String password) {
        // 데이터베이스에서 사용자 정보 조회
        UserDto user = userMapper.loginUser(userId);
        System.out.println(user);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }


         // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 사용자 정보 반환
        return user;
    }

    public String findUserId(String userId){
        return userMapper.findUserId(userId);
    }


}
