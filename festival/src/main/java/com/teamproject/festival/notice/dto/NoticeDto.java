package com.teamproject.festival.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private String noId;
    private String regId;
    private String noTitle;
    private LocalDate noRgDate;
    private String noText;
    private String noFile;
    private Integer noCount;
    private String userName;
    private LocalDate noUdDate;
}
