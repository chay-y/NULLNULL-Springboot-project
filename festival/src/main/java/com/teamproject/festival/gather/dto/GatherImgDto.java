package com.teamproject.festival.gather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatherImgDto {
    public String gatherId;

    private String imgName;

    private String imgUrl;

    public String userId;

    public Integer gtCount;

    public Date gtMadate;

    public Integer ppNum;

    public String gtUseYn;

    public String gatherImg;

    public String ftId;

    public String gtText;
}
