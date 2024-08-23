package com.aeon.hadog.repository;

import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByDiaryId(Long Id);


    void deleteByDiaryId(Long diaryId);

    @Query("SELECT new com.aeon.hadog.base.dto.diary.DiaryDTO(d.emotionTrack.emotionTrackId, d.diaryDate, d.content) FROM Diary d WHERE d.userId = :userId AND d.diaryDate = :date")
    List<DiaryDTO> findDiaryDTOByUserIdAndDiaryDate(@Param("userId") Long userId, @Param("date") LocalDateTime date);
}
