package com.teamproject.festival.gather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatherSearchDto {

    private String gtTitle;

    private String gatherId;

    // 축제 아이디
    private String ftId;

    // 예정일
    private String gtMtdate;

    // 선호 성별
    private List<String> preferGender;

    // 선호 연령대
    private List<String> preferAge;

    // 축제명 도는 축제에 대한 상세 설명
    private String searchBy;

    // 검색어
    private String SearchText;

    private String userId;

    private Integer gtCount;
}
