package com.teamproject.festival.user.dto;

import com.teamproject.festival.user.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String password; // 시큐리티 이용할 땐 반드시 변수명이 password여야 함.
    private String userName;
    private String userEmail;
    private String userUSEYN;
    private String userGender;
    private String userAddress;
    private Role userRole;

}
