package com.teamproject.festival.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalImgDto {
    private Long festivalImgId;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private Long ftId;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
