package com.teamproject.festival.festival.controller;

import com.teamproject.festival.config.PageHandler;
import com.teamproject.festival.festival.dto.FestivalDetailDto;
import com.teamproject.festival.festival.dto.FestivalListDto;
import com.teamproject.festival.festival.dto.FestivalMainDto;
import com.teamproject.festival.festival.dto.FestivalSearchDto;
import com.teamproject.festival.festival.form.FestivalForm;
import com.teamproject.festival.festival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/festival/festivalMainList")
    public List<FestivalMainDto> festivalMainList(Map map) {
        return festivalService.festivalMainList(map);
    }

    //페스티벌 리스트
    @GetMapping(value={"/festival/festivalList", "/festival/festivalList/{page}"})
    public String festivalList(@PathVariable(value = "page", required = false) Integer page,
                               Model model, @ModelAttribute("festivalSearchDto") FestivalSearchDto festivalSearchDto){
        //한 페이지 크기 5 설정
        int ps = 10;

        Map map = new HashMap();

        //현재 페이지 계산
        if (page == null) page=1;

        //조회에 필요한 파라미터 설정
        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("festivalSearchDto", festivalSearchDto);

        //총 페스티벌 개수 조회
        int totalCnt = festivalService.countFestival(map);
        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        //페스티벌 리스트 조회
        List<FestivalListDto> festival = festivalService.festivalListPage(map);

        model.addAttribute("festival", festival);
        model.addAttribute("pageHandler", pageHandler);

        return "festival/festivalList";
    }

    @GetMapping("festival/detail/{ftId}")
    public String festivalDtl(Model model, @PathVariable("ftId") Long ftId){

        FestivalDetailDto festivalDetailDto = festivalService.getFestivalDetail(ftId);

        model.addAttribute("festivalDetail", festivalDetailDto);


        return "festival/festivalDetail";
    }
}
