package com.teamproject.festival.user.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserJoinForm {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String userId;

    @Length(min=4, max=16, message = "비밀번호는 4자이상, 16자 이하로 입력해주세요.")
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;

    @NotEmpty(message = "성별은 필수 입력값입니다.")
    private String userGender;

    @Email(message = "이메일 형식으로 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String userEmail;

}
