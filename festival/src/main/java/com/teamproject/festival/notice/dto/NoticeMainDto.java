package com.teamproject.festival.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMainDto {
    private String noId;

    private String noTitle;

    private Integer noCount;

    private LocalDate noRgDate;
}
