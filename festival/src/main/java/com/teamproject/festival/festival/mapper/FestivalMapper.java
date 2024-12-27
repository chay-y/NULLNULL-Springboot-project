package com.teamproject.festival.festival.mapper;

import com.teamproject.festival.festival.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FestivalMapper {

    List<FestivalMainDto> festivalMainList(Map map);

    //페스티벌 리스트 전체 출력
    List<FestivalListDto> festivalListAll(Map map);

    //페스티벌 리스트
    List<FestivalListDto> festivalList();

    //페스티벌 디테일
    FestivalDetailDto festivalDetail(Long ftId);

    //페스티벌 리뷰
    int festivalReview(FestivalReviewDto festivalReviewDto);

    List<FestivalListDto> festivalListPage(Map map);

    //페스티벌 개수 세기
    int countFestival(Map map);

}
