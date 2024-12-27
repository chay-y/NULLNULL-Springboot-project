    package com.teamproject.festival.gather.service;

    import com.teamproject.festival.festival.dto.FestivalListDto;
    import com.teamproject.festival.gather.dto.GatherDto;
    import com.teamproject.festival.gather.dto.GatherImgDto;
    import com.teamproject.festival.gather.dto.GatherSearchDto;
    import com.teamproject.festival.gather.form.GatherForm;
    import com.teamproject.festival.gather.mapper.GatherMapper;
    import com.teamproject.festival.user.dto.UserDto;
    import com.teamproject.festival.user.mapper.UserMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;

    @Service
    @Transactional(rollbackFor = Exception.class) // 예외 발생 시 롤백
    public class GatherService {

        @Autowired
        private GatherMapper gatherMapper;

        @Autowired
        private UserMapper userMapper;

        @Autowired
        private GatherImgService gatherImgService;

        // controller < List 불러옴
        public List<GatherDto> gatherListAll() {
            return gatherMapper.gatherListAll();
        }

        // 축제 클릭 후 폼에 나타내기 < 디테일 확인
        public GatherDto getGatherDetail(String gatherId) {

            gatherMapper.gatherGtCount(gatherId);

            GatherDto gatherDto = gatherMapper.gatherSelect(gatherId);

            return gatherDto;
        }

        // 축제 등록
        public String gatherInsert(GatherForm gatherForm, String id) {
            GatherDto gatherDto = makeGather(gatherForm);

            UserDto userDto = userMapper.loginUser(id);
            gatherDto.setGatherId(userDto.getUserId());

            gatherMapper.gatherInsert(gatherDto);

            return gatherDto.getGatherId();
        }


        public int countGather(Map map) {
            return gatherMapper.countGather(map);
        }

        public List<GatherSearchDto> gatherListPage(Map map) {
            return gatherMapper.gatherListPage(map);
        }

        // 축제 내용 수정 및 업데이트
        public String updateGather (GatherForm gatherForm) throws Exception {
            GatherDto gatherDto = makeGather(gatherForm);

            int result = gatherMapper.gatherUpdate(gatherDto);

            return gatherDto.getGatherId();
        }

        public String deleteGather (String gatherId) throws Exception {
            int result = gatherMapper.gatherDelete(gatherId);

            if (result == 0) {
                throw new Exception("삭제할 데이터가 없습니다.");
            }
            return gatherId;
        }


        private GatherDto makeGather(GatherForm gatherForm) {
            GatherDto gatherDto = new GatherDto();
            gatherDto.setGatherId(gatherForm.getGatherId());
            gatherDto.setGtTitle(gatherForm.getGtTitle());
            // gatherDto.setGtRgDate(gatherForm.getGtRgDate()); // 등록일은 current_timestamp
            // gatherDto.setGtCount(gatherForm.getGtCount());
            gatherDto.setUserId(gatherForm.getUserId());
            gatherDto.setGtText(gatherForm.getGtText());

            List<String> preferGender = gatherForm.getPreferGender() != null ? gatherForm.getPreferGender() : new ArrayList<>();
            List<String> preferAge = gatherForm.getPreferAge() != null ? gatherForm.getPreferAge() : new ArrayList<>();

            gatherDto.setPreferGender(String.join(",", preferGender));
            gatherDto.setPreferAge(String.join(",", preferAge));
            gatherDto.setGtMtDate(gatherForm.getGtMtDate());

            gatherDto.setPpNum(gatherForm.getPpNum());

            return gatherDto;
        }

        // Detail 코드 내 들어가는 폼
        private GatherForm makeGatherForm(GatherDto gatherDto) {
            GatherForm gatherForm = new GatherForm();
            gatherForm.setGatherId(gatherDto.getGatherId());
            gatherForm.setGtTitle(gatherDto.getGtTitle());
            // gatherForm.setGtRgDate(gatherDto.getGtRgDate());
            // gatherForm.setGtCount(gatherDto.getGtCount()); << 조회수
            gatherForm.setUserId(gatherDto.getUserId());
            gatherForm.setGtText(gatherDto.getGtText());
//            gatherForm.setPreferGender(gatherDto.getPreferGender());
//            gatherForm.setPreferAge(gatherDto.getPreferAge());
            gatherForm.setPpNum(gatherDto.getPpNum());

            return gatherForm;
        }

        // 동행 페이지 개수세기
        public int gatherSearchCount(Map map) {
            return gatherMapper.gatherSearchCount(map);
        }


    }

