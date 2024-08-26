package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.exception.BlanckContentException;
import com.aeon.hadog.base.exception.EmotionTrackNotBelongToUserException;
import com.aeon.hadog.domain.Diary;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class DiaryServiceTest {

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Nested
    @DisplayName("일기 생성 테스트")
    class createDiaryTest {

        @Test
        @DisplayName("일기 생성 성공")
        void createDiary() {
            // given
            String userId = "user3";
            DiaryDTO dto = DiaryDTO.builder()
                    .emotionTrackId(1L)
                    .content("해피가 행복해보인다")
                    .build();

            // when
            Long diaryId = diaryService.createDiary(userId, dto);

            // then
            assertNotNull(diaryId);
            Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow();
            assertEquals(diary.getDiaryId(), diaryId);
        }

        @Test
        @DisplayName("일기 생성 실패")
        void createDiary_userError() {
            // given
            String userId = "user2";
            DiaryDTO dto = DiaryDTO.builder()
                    .emotionTrackId(1L)
                    .content("해피가 행복해보인다")
                    .build();

            // when
            Exception exception = assertThrows(EmotionTrackNotBelongToUserException.class, () -> {
                diaryService.createDiary(userId, dto);
            });

            // then
            assertEquals(ErrorCode.EMOTION_TRACK_NOT_BELONG_TO_USER_ERROR, ((EmotionTrackNotBelongToUserException) exception).getErrorCode());
        }

    }


    @Nested
    @DisplayName("일기 상세 불러오기 테스트")
    class getDiaryTest {
        @Test
        @DisplayName("상세 불러오기 성공")
        void getDiary() {
            // given
            String userId = "user3";
            Long diaryId = 3L;
            Diary diary = diaryRepository.findByDiaryId(3L).orElseThrow();

            // when
            DiaryDTO dto = diaryService.getDiary(userId, diaryId);

            // then
            assertNotNull(dto);
            assertEquals(diary.getEmotionTrack().getEmotionTrackId(), dto.getEmotionTrackId());
            assertEquals(diary.getDiaryDate(), dto.getDiaryDate());
            assertEquals(diary.getContent(), dto.getContent());
        }

        @Test
        @DisplayName("상세 불러오기 실패")
        void getDiary_userError() {
            // given
            String userId = "user2";
            Long diaryId = 3L;

            // when
            Exception exception = assertThrows(EmotionTrackNotBelongToUserException.class, () -> {
                diaryService.getDiary(userId, diaryId);
            });

            // then
            assertEquals(ErrorCode.EMOTION_TRACK_NOT_BELONG_TO_USER_ERROR, ((EmotionTrackNotBelongToUserException) exception).getErrorCode());

        }

    }

    @Nested
    @DisplayName("일기 수정하기 테스트")
    class modifyDiaryTest {
        @Test
        @DisplayName("일기 수정 테스트")
        void modifyDiary() {
            // given
            String userId = "user3";
            Long diaryId = 3L;
            String content = "해피가 슬퍼보인다.";

            // when
            DiaryDTO dto = diaryService.modifyDiary(userId, diaryId, content);
            Diary diary = diaryRepository.findByDiaryId(3L).orElseThrow();

            // then
            assertNotNull(dto);
            assertEquals(diary.getEmotionTrack().getEmotionTrackId(), dto.getEmotionTrackId());
            assertEquals(diary.getDiaryDate(), dto.getDiaryDate());
            assertEquals(diary.getContent(), dto.getContent());
        }

        @Test
        @DisplayName("일기 수정 실패 테스트 - userError")
        void modifyDiary_userError() {
            // given
            String userId = "user2";
            Long diaryId = 3L;
            String content = "해피가 슬퍼보인다.";

            // when
            Exception exception = assertThrows(EmotionTrackNotBelongToUserException.class, () -> {
                diaryService.modifyDiary(userId, diaryId, content);
            });

            // then
            assertEquals(ErrorCode.EMOTION_TRACK_NOT_BELONG_TO_USER_ERROR, ((EmotionTrackNotBelongToUserException) exception).getErrorCode());

        }

        @Test
        @DisplayName("일기 수정 실패 테스트 - valueError")
        void modifyDiary_valueError() {
            // given
            String userId = "user3";
            Long diaryId = 3L;
            String content = "";

            // when
            Exception exception = assertThrows(BlanckContentException.class, () -> {
                diaryService.modifyDiary(userId, diaryId, content);
            });

            // then
            assertEquals(ErrorCode.BLANK_CONTENT_ERROR, ((BlanckContentException) exception).getErrorCode());

        }

    }

    @Test
    @DisplayName("일기 목록 테스트")
    void getDiarys() {
        // given
        String userId = "user3";
        LocalDate date = LocalDate.of(2024, 5, 2);
        List<DiaryDTO> diaries = diaryRepository.findDiaryDTOByUserIdAndDiaryDate(4L, date);

        // when
        List<DiaryDTO> dtos = diaryService.getDiarys(userId, date);

        // then
        assertNotNull(dtos);
        assertEquals(diaries.size(), dtos.size());
        assertEquals(diaries.get(0).getContent(), dtos.get(0).getContent());
        assertEquals(diaries.get(1).getContent(), dtos.get(1).getContent());
    }
}