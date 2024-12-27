package com.teamproject.festival.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalMainDto {
    private String ftName;
    private Date ftStDate;
    private Date ftEdDate;
    private String ftText;
    private Integer ftId;
    private String ftImg;
}
