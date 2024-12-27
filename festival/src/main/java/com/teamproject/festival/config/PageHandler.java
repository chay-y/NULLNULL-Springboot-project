package com.teamproject.festival.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PageHandler {
    private int totalCnt;
    private int pageSize;
    private int naviSize = 10;
    private int totalPage;
    private int page;
    private int beginPage;
    private int endPage;

    private boolean firstPage;
    private boolean lastPage;

    public PageHandler(int totalCnt, int pageSize, int page) {
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        // 전체 페이지 수 계산(totalPage)
        totalPage = (int) Math.ceil((double) totalCnt / pageSize);

        // 시작 페이지 번호(beginPage)
        beginPage = (page-1) / naviSize * naviSize + 1;

        // 마지막 페이지 번호(endPage)
        endPage = Math.min(beginPage + naviSize - 1, totalPage);

        // 시작페이지인지 확인(firstPage)
        firstPage = (beginPage == 1);

        // 마지막 페이지인지 확인(lastPage)
        lastPage = (endPage == totalPage);
    }
}
