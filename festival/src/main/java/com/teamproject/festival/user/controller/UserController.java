package com.teamproject.festival.user.controller;

import com.teamproject.festival.user.constant.Role;
import com.teamproject.festival.user.dto.UserDto;
import com.teamproject.festival.user.form.UserJoinForm;
import com.teamproject.festival.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 폼 불러오기
    @GetMapping("/new")
    public String userForm(Model model, HttpServletRequest request) {

//        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
//        System.out.println(token.getHeaderName() + "=" + token.getToken());

        model.addAttribute("userJoinForm", new UserJoinForm()); //유효성검사를위해 MemberJoinForm

        return "login/signIn";
    }

    //회원가입
    @PostMapping("/new")
    public String newMember(@Valid UserJoinForm userJoinForm,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes rttr) {

        if(bindingResult.hasErrors()){
            return "login/signIn";
        }

        try {
            //회원가입

            UserDto dto = new UserDto();

            dto.setUserId(userJoinForm.getUserId());
            dto.setPassword(userJoinForm.getPassword());
            dto.setUserName(userJoinForm.getUserName());
            dto.setUserGender(userJoinForm.getUserGender());
            dto.setUserEmail(userJoinForm.getUserEmail());
            dto.setUserRole(Role.USER);

            userService.insertUser(dto);

            rttr.addFlashAttribute("resultMessage", "회원가입환영"); //화면에 메세지 전달

            System.out.println(userJoinForm.toString());


        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "login/signIn";
        }
        return "redirect:/";
    }

    // 헤더에 있는 로그인 버튼 클릭시 이동
    @GetMapping("/login")
    public String loginForm(){
        return "/login/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestParam String userId, String password,Model model) {
        try {
            // 로그인 처리
            UserDto user = userService.login(userId, password);

            // 사용자 정보를 모델에 추가
            model.addAttribute("user", user);

            // 로그인 성공 시 메인 페이지로 이동
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러메세지와 함께 로그인 페이지로 다시 이동
            model.addAttribute("loginErrorMsg", e.getMessage());
            return "login/login";
        }
    }

    //로그인 에러
    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/login/login";
    }

}
