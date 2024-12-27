package com.teamproject.festival.notice.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeForm {

    private String regId;

    @NotBlank(message = "제목은 필수 입력 값 입니다.")
    private String noTitle;

    @NotBlank(message = "내용은 필수 입력 값 입니다.")
    private String noText;

    private String noFile;

    private String noId;
}
