package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name = "emotion_track_id", nullable = false)
    private EmotionTrack emotionTrack;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable=false)
    private LocalDate diaryDate;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;
}
