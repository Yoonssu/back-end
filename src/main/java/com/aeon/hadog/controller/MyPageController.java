package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.emotionTrack.EmotionTrackDTO;
import com.aeon.hadog.base.dto.mypage.MyPageDTO;
import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.service.AdoptPostService;
import com.aeon.hadog.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final AdoptPostService adoptPostService;

    @GetMapping("/userinfo")
    public ResponseEntity<ResponseDTO> getUserInfo(@AuthenticationPrincipal String user) {
        try {
            MyPageDTO userInfo = myPageService.getUserInfo(user);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "사용자 정보 조회 성공", userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "사용자 정보 조회 실패: " + e.getMessage(), null));
        }
    }

    @GetMapping("/petinfo")
    public ResponseEntity<ResponseDTO> getPetInfo(@AuthenticationPrincipal String user) {
        try {
            List<PetDTO> petInfo = myPageService.getPetInfo(user);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "반려견 정보 조회 성공", petInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "반려견 정보 조회 실패: " + e.getMessage(), null));
        }
    }

    @PutMapping("/nickname")
    public ResponseEntity<ResponseDTO> updateNickname(@AuthenticationPrincipal String user, @RequestParam String newNickname) {

        try {
            myPageService.updateNickname(user, newNickname);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "닉네임 수정 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "닉네임 수정 실패: " + e.getMessage(), null));
        }
    }

    // 사용자가 작성한 입양 후기 목록을 조회하는 API 엔드포인트
    @GetMapping("/adoptreviews")
    public ResponseEntity<ResponseDTO> getAdoptReviewsByUser(@AuthenticationPrincipal String userId) {
        try {
            List<AdoptReviewDTO> adoptReviews = myPageService.getAdoptReviewsByUserId(userId);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "사용자 입양 후기 목록 조회 성공", adoptReviews));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "사용자 입양 후기 목록 조회 실패: " + e.getMessage(), null));
        }
    }

    @GetMapping("/adoptposts")
    public ResponseEntity<ResponseDTO> getMyAdoptPosts(@AuthenticationPrincipal String userId) {
        try {
            List<AdoptPostDTO> adoptPosts = myPageService.getMyAdoptPosts(userId);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "내가 작성한 임시 보호글 목록 조회 완료", adoptPosts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO<>(400, false, "내가 작성한 임시 보호글 목록 조회 중 오류 발생"+ e.getMessage(), null));
        }
    }

    // 사용자의 감정 추적 목록을 조회하는 API 엔드포인트
    @GetMapping("/emotion-tracks/{petId}")
    public ResponseEntity<ResponseDTO> getEmotionTracks(@PathVariable Long petId) {
        try {
            List<EmotionTrackDTO> emotionTracks = myPageService.getEmotionTracksByPetId(petId);

            if (emotionTracks.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO<>(404, false, "해당 반려견 ID에 대한 감정 추적 기록이 없습니다.", null));
            }

            return ResponseEntity.ok(new ResponseDTO<>(200, true, "감정 추적 기록 조회 완료", emotionTracks));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ResponseDTO<>(500, false, "감정 추적 기록 조회 중 오류 발생: " + e.getMessage(), null));
        }
    }


}




