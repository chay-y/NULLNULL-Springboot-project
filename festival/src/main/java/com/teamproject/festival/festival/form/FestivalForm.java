package com.teamproject.festival.festival.form;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FestivalForm {

    private Integer ftId;
    private String ftName;
    private LocalDate ftStdate;
    private LocalDate ftEddate;
    private String ftText;
    private String ftHost;
    private String ftPhone;
    private String ftAddress;
    private String ftImg;
    private String ftLatitude;
    private String ftLongtitude;
}

