package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="emotion_track")
public class EmotionTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionTrackId;

    @Column(nullable=false)
    private Long petId;

    @Column(nullable=false)
    private LocalDateTime emotionDate;

    @Column(name = "emotion_id", nullable = false)
    private Long emotionId; // 여전히 존재하지만, 관계 설정 시 충돌 방지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", insertable = false, updatable = false)
    private Emotion emotion;
}
