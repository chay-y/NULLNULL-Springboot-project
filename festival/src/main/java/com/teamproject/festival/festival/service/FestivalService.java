package com.teamproject.festival.festival.service;

import com.teamproject.festival.festival.dto.*;
import com.teamproject.festival.festival.form.FestivalForm;
import com.teamproject.festival.festival.mapper.FestivalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FestivalService {

    @Autowired
    private FestivalMapper festivalMapper;

    // 메인페이지에 있는 축제 리스트
    public List<FestivalMainDto> festivalMainList(Map map) {
        return festivalMapper.festivalMainList(map);
    }

    // 전체 출력
    public List<FestivalListDto> festivalListAll(Map map) {
        return festivalMapper.festivalListAll(map);
    }

    public List<FestivalListDto> festivalList(){
        return festivalMapper.festivalList();
    }

    public FestivalDetailDto getFestivalDetail(Long ftId){

        FestivalDetailDto festivalDetailDto = festivalMapper.festivalDetail(ftId);

        return festivalDetailDto;
    }

    public int festivalReview(FestivalReviewDto festivalReviewDto){

        return festivalMapper.festivalReview(festivalReviewDto);
    }




    //상품 검색하고 폼에 나타내기
    public FestivalForm getFestivalDtl(Long ftId){

        //상품 조회
        FestivalDetailDto FestivaldetailDto = festivalMapper.festivalDetail(ftId);

        //만약 상품이 존재하지 않으면 NullPointerException 을 발생시킴
        if(FestivaldetailDto == null){
            throw  new NullPointerException("상품이 존재 하지 않습니다.");
        }

        //읽어온 내용을 폼의 형식으로 변환(화면에 표현하기위해)
        FestivalForm festivalForm = makeFestivalForm(FestivaldetailDto);

        //이미지를 설정
//        festivalForm.setfestivalImgList(festivalMapper.festivalImgSelect(ftId));


        //폼의 형태로 리턴
        return festivalForm;
    }

    private FestivalForm makeFestivalForm(FestivalDetailDto festivalDetailDto){
        FestivalForm festivalForm = new FestivalForm();
        festivalForm.setFtId(festivalDetailDto.getFtId());
        festivalForm.setFtName(festivalDetailDto.getFtName());
        festivalForm.setFtText(festivalDetailDto.getFtText());
        festivalForm.setFtStdate(festivalDetailDto.getFtStdate());
        festivalForm.setFtEddate(festivalDetailDto.getFtEddate());
        festivalForm.setFtHost(festivalDetailDto.getFtHost());
        festivalForm.setFtAddress(festivalDetailDto.getFtAddress());

        return festivalForm;
    }

    // 페스티벌 페이지 개수세기
    public int countFestival(Map map) {
        return festivalMapper.countFestival(map);
    }

    // 페스티벌 검색
    public List<FestivalListDto> festivalListPage(Map map){
        return festivalMapper.festivalListPage(map);

    }

}
