package com.teamproject.festival.gather.form;

import com.teamproject.festival.gather.dto.GatherImgDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.w3c.dom.Text;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatherForm {
    private String gatherId; // 동행 아이디

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gtMtDate; // 예정일

    private String gtTitle; // 제목

    private String userId; // 회원 아이디

    private List<String> preferGender;
    //private String preferGender; // 선호 성별

    private List<String> preferAge;
    //private String preferAge; // 선호 연령

    private String gtText; // 안의 내용

    private Integer ppNum; // 동행 인원수






}
