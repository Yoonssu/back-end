package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ResponseDTO> uploadDiary(@AuthenticationPrincipal String userId, @Valid @RequestBody DiaryDTO diaryDTO){
        Long diaryId = diaryService.createDiary(userId, diaryDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 작성 완료", diaryId));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> DetailDiary(@AuthenticationPrincipal String userId, @RequestParam Long diaryId){
        DiaryDTO result = diaryService.getDiary(userId, diaryId);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 상세 불러오기 완료", result));
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO> modifyDiary(@AuthenticationPrincipal String userId, @RequestParam Long diaryId, @RequestParam String content){
        DiaryDTO result = diaryService.modifyDiary(userId, diaryId, content);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 수정 완료", result));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getDiarys(@AuthenticationPrincipal String userId, @RequestParam LocalDateTime date){
        List<DiaryDTO> result = diaryService.getDiarys(userId, date);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 목록 불러오기 완료", result));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteDiary(@AuthenticationPrincipal String userId, @RequestParam Long diaryId){
        Boolean isDelete = diaryService.deleteDiary(userId, diaryId);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 삭제 완료", isDelete));
    }
}
