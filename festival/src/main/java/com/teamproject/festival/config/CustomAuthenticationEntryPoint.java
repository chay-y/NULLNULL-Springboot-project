package com.teamproject.festival.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;


// 인증되지 않은 사용자가 접근을 시도할 때 적절한 응답을 생성하여 클라이언트에게 전달
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 인증이 필요한 메서드에 접근하면 호출되며,
    // 인증 예외가 발생한 경우에 실행
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 알람창 띄우고 로그인창으로 이동
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script> alert('로그인 후 이용 가능합니다.');" +
                " window.location = 'http://localhost:8080/users/login';</script>");
        out.close();
    }
}