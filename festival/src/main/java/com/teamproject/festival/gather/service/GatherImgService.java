package com.teamproject.festival.gather.service;

import com.teamproject.festival.gather.dto.GatherImgDto;
import com.teamproject.festival.gather.mapper.GatherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GatherImgService {

    private String gatherImgLocation;

    private final GatherMapper gatherMapper;

    private final FileService fileService;

    public void saveGatherImg(GatherImgDto gatherImgDto, MultipartFile gatherImgFile) throws Exception {

        // 파일의 원본 이름 저장
        String gatherImg = gatherImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(gatherImgDto);

        // 파일 업로드
        if (!StringUtils.isEmpty(gatherImg)) {
            // 사용자가 축제 이미지를 등록하면 uploadFile 메서드를 호출
            imgName = fileService.uploadFile(gatherImgLocation, gatherImg, gatherImgFile.getBytes());
            // 저장한 축제 이미지를 불러올 경로 설정
            imgUrl = "/images/gather/" + imgName;
        }
        // 축제 이미지에 대한 정보 저장
        gatherImgDto.setImgName(imgName);
        gatherImgDto.setGatherImg(gatherImg);
        gatherImgDto.setImgUrl(imgUrl);
        // 축제 이미지 입력 처리
        gatherMapper.gatherImgInsert(gatherImgDto);
    }

    public void gatherImgUpdate(Long gatherImgId, MultipartFile gatherImgFile) throws Exception {

        if (!gatherImgFile.isEmpty()) {
            // 이미지 읽어오기
            GatherImgDto savedGatherImg = gatherMapper.gatherImgIdSelect(gatherImgId);
            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedGatherImg.getImgName())) {
                fileService.deleteFile(gatherImgLocation + "/" + savedGatherImg.getImgName());
            }
            // 새로운 이미지 저장
            String gatherImg = gatherImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(gatherImgLocation, gatherImg, gatherImgFile.getBytes());
            String imgUrl = "/images/gather/" + imgName;

            savedGatherImg.setGatherImg(gatherImg);
            savedGatherImg.setImgName(imgName);
            savedGatherImg.setImgUrl(imgUrl);

            gatherMapper.gatherImgUpdate(savedGatherImg);
        }
    }
}
