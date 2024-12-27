package com.teamproject.festival.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor //기본생성자
@AllArgsConstructor
public class FestivalListDto {

    private Long ftId;
    private String ftName;
    private String ftText;
    private LocalDate ftStdate;
    private LocalDate ftEddate;
    private String ftHost;
    private String ftPhone;
    private String ftImg;
}
