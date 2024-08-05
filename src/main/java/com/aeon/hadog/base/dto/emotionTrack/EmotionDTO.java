package com.aeon.hadog.base.dto.emotionTrack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionDTO {
    private Long emotionId;
    private String emotion;
    private String description;
    private String image;
}
