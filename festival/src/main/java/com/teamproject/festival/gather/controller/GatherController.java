package com.teamproject.festival.gather.controller;

import com.teamproject.festival.config.PageHandler;
import com.teamproject.festival.gather.dto.GatherDto;
import com.teamproject.festival.gather.dto.GatherSearchDto;
import com.teamproject.festival.gather.form.GatherForm;
import com.teamproject.festival.gather.service.GatherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GatherController {

    @Autowired
    private GatherService gatherService;

    // 메인 페이지에서 '같이가요 축제' 를 누르면 축제 폼 이동 (리스트도 같이 나온다)
    @GetMapping(value = {"/gather/gatherList/new", "/gather/gatherList/{page}"})
    public String gatherList(@PathVariable(value = "page", required = false) Integer page,
                             Model model, @ModelAttribute("gatherSearchDto") GatherSearchDto gatherSearchDto) {

        int ps = 10;

        Map map = new HashMap();

        if (page == null || page < 1) {
            page = 1;
        }

        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("gatherSearchDto", gatherSearchDto);

        int totalCnt = gatherService.countGather(map);
        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        List<GatherSearchDto> gather = gatherService.gatherListPage(map);

        model.addAttribute("gather", gather);
        model.addAttribute("pageHandler", pageHandler);

        System.out.println("Page parameter: " + page);

        return "/gather/gatherList";
    }

    @GetMapping("/gather/gatherList")
    public String gatherList(Model model) {
        List<GatherDto> gatherList = gatherService.gatherListAll();
        model.addAttribute("gatherList", gatherList);
        return "/gather/gatherList";
    }

    // 등록되어있는 목록을 누르면 해당 gatherDetail 를 볼 수 있다.
    // 해당 gatherDetail을 보고 수정 버튼을 누르면 gatherId를 기반으로 수정이 가능하다
    @GetMapping("/gather/{gatherId}")
    public String gatherDetail(Model model, @PathVariable("gatherId") String gatherId) {

        GatherDto gatherDto = gatherService.getGatherDetail(gatherId);

        model.addAttribute("gather", gatherDto);
        model.addAttribute("gatherId", gatherId);

        return "gather/gatherDetail";
    }

    // 수정
    @GetMapping("/gather/{gatherId}/form")
    public String gatherUpdateForm(Model model, @PathVariable("gatherId") String gatherId) {
        GatherDto gatherDto = gatherService.getGatherDetail(gatherId);

        GatherForm gatherForm = new GatherForm();
        gatherForm.setGatherId(gatherDto.getGatherId());
        gatherForm.setGtTitle(gatherDto.getGtTitle());
        gatherForm.setGtMtDate(gatherDto.getGtMtDate());
        gatherForm.setUserId(gatherDto.getUserId());
        gatherForm.setGtText(gatherDto.getGtText());
        gatherForm.setPpNum(gatherDto.getPpNum());

        model.addAttribute("gatherForm", gatherForm);
        model.addAttribute("gatherId", gatherId);

        return "gather/gatherForm";
    }

    @PostMapping("/gather/updateGather/{gatherId}")
    public String gatherUpdate(@Valid GatherForm gatherForm, BindingResult bindingResult,
                               Model model, @PathVariable("gatherId") String gatherId) {
        if (bindingResult.hasErrors()) {
            return "gather/gatherForm";
        }

        try{
            gatherForm.setGatherId(gatherId);
            gatherService.updateGather(gatherForm);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            e.printStackTrace();
            return "gather/gatherForm";
        }
        return "redirect:/gather/" + gatherForm.getGatherId();
    }

    // 삭제
    @PostMapping("/gather/{gatherId}")
    public String gatherDelete(@PathVariable String gatherId) throws Exception {
        gatherService.deleteGather(gatherId);

        return "redirect:/gather/gatherList";
    }

    // 동행자를 등록
    @GetMapping("gather/newGather")
    public String gatherForm(Model model, Principal principal) {
        String userId = (principal != null) ? principal.getName() : null;

        model.addAttribute("gatherForm", new GatherForm());
        model.addAttribute("userId", userId);
        model.addAttribute("gatherId", null);

        return "gather/gatherForm";
    }

    @PostMapping("gather/newGather")
    public String newGather(@Valid GatherForm gatherForm, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "gather/gatherForm";
        }

        String loggedInUserId = principal.getName();
        System.out.println("gtMtDate  : " + gatherForm.getGtMtDate());

        try {
            System.out.println("로그인 사용자 ID : " + loggedInUserId);

            String gatherId = generateGatherId();
            gatherForm.setGatherId(gatherId);
            gatherForm.setUserId(loggedInUserId);

            gatherService.gatherInsert(gatherForm, loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "동행 등록 중 에러가 발생했습니다.");
            return "gather/gatherForm";
        }
        return "redirect:/";
    }

    private String generateGatherId() {
        return UUID.randomUUID().toString();
    }

}