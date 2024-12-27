package com.teamproject.festival.gather.mapper;

import com.teamproject.festival.festival.dto.FestivalListDto;
import com.teamproject.festival.gather.dto.GatherImgDto;
import com.teamproject.festival.gather.dto.GatherDto;
import com.teamproject.festival.gather.dto.GatherSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GatherMapper {

    // 동행 추가
    int gatherInsert(GatherDto gatherDto);

    // 동행 전체 리스트
    List<GatherDto> gatherListAll();

    // 동행 조회수
    void gatherGtCount(String gatherId);

    // 눌렀을 때 해당 동행목록을 보임
    GatherDto gatherSelect(String gatherId);

    // 검색 리스트
    List<GatherSearchDto> gatherListPage(Map map);

    // 동행 게시물 개수
    int countGather(Map map);

    // 동행 수정
    int gatherUpdate(GatherDto gatherDto);

    // 동행 삭제
    int gatherDelete(String gatherId);



    // 이미지 부분

    int gatherImgInsert(GatherImgDto gatherImgDto);

    GatherImgDto gatherImgIdSelect(Long gatherImgId);

    int gatherImgUpdate(GatherImgDto gatherImgDto);

    // 동행 검색 개수 세기
    int gatherSearchCount(Map map);
}
