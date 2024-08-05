package com.aeon.hadog.repository;

import com.aeon.hadog.domain.EmotionTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmotionTrackRepository extends JpaRepository<EmotionTrack, Long> {
    //단일 조회
    Optional<EmotionTrack> findByEmotionTrackId(Long Id);

    @Query("SELECT et FROM EmotionTrack et JOIN FETCH et.emotion WHERE et.petId = :petId")
    List<EmotionTrack> findByPetIdWithEmotion(Long petId);
}