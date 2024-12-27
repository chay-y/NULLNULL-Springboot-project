package com.teamproject.festival.notice.mapper;

import com.teamproject.festival.notice.dto.NoticeDto;
import com.teamproject.festival.notice.dto.NoticeMainDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {

    // 공지사항 전체 출력
    List<NoticeDto> noticeListAll(Map map);

    // 메인페이지 공지사항 출력
    List<NoticeMainDto> noticeMainSelect(Map map);

    // 공지사항 개수
    int countNotice(Map map);

    // 공지사항 세부정보
    NoticeDto noticeDetail(String noId);

    // 조회수 증가
    int updateCount(String noId);

    // 공지사항 작성
    int noticeInsert(NoticeDto noticeDto);

    // 작성자 id 찾기
    String findUserId(String noId);

    // 공지사항 삭제
    int noticeDelete(String noId);

    // 공지사항 수정
    int noticeUpdate(NoticeDto noticeDto);
}
