package com.teamproject.festival;

import com.teamproject.festival.config.PageHandler;
import com.teamproject.festival.festival.dto.FestivalMainDto;
import com.teamproject.festival.festival.dto.FestivalSearchDto;
import com.teamproject.festival.festival.service.FestivalService;
import com.teamproject.festival.notice.dto.NoticeDto;
import com.teamproject.festival.notice.dto.NoticeMainDto;
import com.teamproject.festival.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final NoticeService noticeService;
    private final FestivalService festivalService;
    private final PageHandler pageHandler;

    @GetMapping("/")
    public String main(@RequestParam(value="page", required = false) Integer page,
                       @RequestParam(value = "setFtAddress", required = false) String searchFestival,
                       @ModelAttribute("festivalSearchDto") FestivalSearchDto festivalSearchDto,
                       Model model)  {

        // 페이징 처리 계산
        int pg = 5;
        if (page == null) page = 1;

        Map map = new HashMap();

        map.put("page", page * pg - pg);
        map.put("pageSize", pg);

        if(searchFestival ==null ){
            searchFestival = "";
        }
        festivalSearchDto.setSearchftAddress(searchFestival);
        festivalSearchDto.setSearchftName("searchftName");


        map.put("festivalSearchDto", festivalSearchDto);

        //페이지 핸들러 설정
        int totalCnt = noticeService.countNotice(map);

        PageHandler pageHandler = new PageHandler(totalCnt, pg, page);

        List<NoticeMainDto> notice = noticeService.noticeMainSelect(map);

        Map map2 = new HashMap();

        List<FestivalMainDto> festival = festivalService.festivalMainList(map2);

        model.addAttribute("notice", notice);
        model.addAttribute("festival", festival);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("maxPage", 10);

        return "/index";
    }
}
