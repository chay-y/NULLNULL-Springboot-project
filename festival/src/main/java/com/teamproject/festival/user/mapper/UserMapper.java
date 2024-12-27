package com.teamproject.festival.user.mapper;


import com.teamproject.festival.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 회원가입
    int insertUser(UserDto userDto);

    // 아이디 중복 확인
    UserDto overlapId(String userId);

    // 이메일 중복 확인
    UserDto overlapEmail(String email);

    // 로그인
    UserDto loginUser(String userId);

    // id 찾기(현재는 필요없음)
    String findUserId(String userId);

}
