package com.aeon.hadog.base.dto.emotionTrack;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmotionTrackDTO {
    private Long emotionTrackId;
    private Long petId;
    private LocalDateTime emotionDate;
    private Long emotionId;
    private EmotionDTO emotion;
}
