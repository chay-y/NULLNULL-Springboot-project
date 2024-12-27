package com.teamproject.festival.notice.controller;

import com.teamproject.festival.config.PageHandler;
import com.teamproject.festival.notice.dto.NoticeDto;
import com.teamproject.festival.notice.form.NoticeForm;
import com.teamproject.festival.notice.service.NoticeService;
import com.teamproject.festival.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;

    @Autowired
    private final UserService userService;

    // 공지사항 리스트 출력
    @GetMapping(value = {"/notices", "/notices/{page}"})
    public String noticeList(@PathVariable(value = "page", required = false) Integer page,
                             Model model, Principal principal) {
        int ps = 10; // 페이지 크기

        // 현재 페이지 계산
        if (page == null) page = 1;

        // 조회에 필요한 파라미터 설정
        Map<String, Object> map = new HashMap<>();
        map.put("page", page * ps - ps);
        map.put("pageSize", ps);

        // 총 공지사항 개수 조회
        int totalCnt = noticeService.countNotice(map);
        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        // 공지사항 리스트 조회
        List<NoticeDto> notices = noticeService.noticeListAll(map);

        // 로그인 사용자 아이디 가져오기
        String regId = (principal != null) ? principal.getName() : null;

        // 모델에 데이터 추가
        model.addAttribute("notices", notices);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("regId", regId);

        return "notice/noticeList";
    }

    // 공지사항 상세 보기
    @GetMapping("/notice/{noId}")
    public String noticeDetail(@PathVariable("noId") String noId,
                               HttpServletRequest request,
                               Model model) {
        // 세션을 사용해 조회수 증가 처리
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<String> viewedNotices = (List<String>) session.getAttribute("viewedNotices");

        if (viewedNotices == null) {
            viewedNotices = new ArrayList<>();
        }

        if (!viewedNotices.contains(noId)) {
            noticeService.updateCount(noId);
            viewedNotices.add(noId);
            session.setAttribute("viewedNotices", viewedNotices);
        }

        // 공지사항 상세 조회
        NoticeDto noticeDto = noticeService.noticeDetail(noId);
        model.addAttribute("notice", noticeDto);

        return "notice/noticeDetail";
    }

    // 공지사항 작성 폼
    @GetMapping("/notice/write")
    public String noticeForm(Model model, Principal principal) {
        String userId = (principal != null) ? principal.getName() : null;

        if (userId == null) {
            return "redirect:/login"; // 로그인이 필요한 경우 처리
        }

        model.addAttribute("noticeForm", new NoticeForm());
        model.addAttribute("regId", userId);

        return "/notice/noticeWrite";
    }

    // 공지사항 작성 처리
    @PostMapping("/notice/write")
    public String noticeWrite(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                              Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "/notice/write";
        }

        String id = principal.getName();

        try {
            // 로그인 사용자 ID 확인 및 설정
            String loggedInUserId = principal.getName();
            System.out.println("로그인 사용자 ID: " + loggedInUserId); // 디버깅

            // 로그인 사용자 ID 설정
            noticeForm.setRegId(loggedInUserId);

            // 공지사항 저장
            noticeService.noticeInsert(noticeForm, id);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생하였습니다.");
            return "/notice/write";
        }

        return "redirect:/notices";
    }

    // 공지사항 삭제
    @PostMapping("/notice/{noId}/cancel")
    public ResponseEntity<Map<String, Object>> deleteNotice(@PathVariable("noId") String noId,
                                                            Principal principal) {
        // 현재 로그인 사용자 ID
        String loggedInUserId = principal.getName();

        // 삭제 권한 검증: 공지사항 작성자와 로그인 사용자 ID 비교
        NoticeDto notice = noticeService.noticeDetail(noId);
        if (!notice.getRegId().equals(loggedInUserId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "공지사항 삭제 권한이 없습니다.");
            response.put("status", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        try {
            // 공지사항 삭제 처리
            noticeService.noticeDelete(noId);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "공지사항 삭제 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 성공적으로 삭제된 경우 JSON 반환
        Map<String, Object> response = new HashMap<>();
        response.put("message", "공지사항이 삭제되었습니다.");
        response.put("deletedNoticeId", noId);
        response.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 공지사항 수정 페이지로 이동
    @GetMapping("/notice/update/{noId}")
    public String updateForm(@PathVariable("noId") String noId, Model model) {
        NoticeDto notice = noticeService.noticeDetail(noId);
        model.addAttribute("notice", notice);
        return "notice/noticeUpdate";
    }

    // 공지사항 수정
    @PostMapping("/notice/update/{noId}")
    public String updateNotice(@PathVariable("noId") String noId,
                               @ModelAttribute("noticeForm") @Valid NoticeForm noticeForm,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {
        if (bindingResult.hasErrors()) {
            // 폼 에러가 있을 경우 다시 수정 페이지로 보냄
            model.addAttribute("notice", noticeForm);
            return "notice/noticeUpdate";
        }

        try {
            // 작성자 검증 (현재 로그인 사용자와 작성자 비교)
            NoticeDto existingNotice = noticeService.noticeDetail(noId);
            if (!existingNotice.getRegId().equals(principal.getName())) {
                model.addAttribute("errorMessage", "수정 권한이 없습니다.");
                return "notice/noticeUpdate";
            }

            // NoticeForm에 noId를 설정해 업데이트
            noticeForm.setNoId(noId);
            noticeService.noticeUpdate(noticeForm);

            // 수정 후 리스트 페이지로 리다이렉트
            return "redirect:/notices";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "공지사항 수정 중 오류가 발생했습니다.");
            return "notice/noticeUpdate";
        }
    }
}
