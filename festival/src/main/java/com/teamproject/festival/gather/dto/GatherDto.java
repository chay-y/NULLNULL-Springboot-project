package com.teamproject.festival.gather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
// 축제 동행 리스트
public class GatherDto {
    private String gatherId;
    // 동행 아이디
    private String gtTitle;
    // 제목
    private LocalDateTime gtRgDate;
    // 등록일
    private String preferGender;
    // 선호 성별
    private String preferAge;
    // 선호 연령
    private String gtImg;
    // 이미지
    private Integer gtCount;
    // 조회수

    private String userId;

    private String gtText;

    private Integer ppNum;

    private String gtUseYn;

    private Integer ftId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gtMtDate; // 예정일

}