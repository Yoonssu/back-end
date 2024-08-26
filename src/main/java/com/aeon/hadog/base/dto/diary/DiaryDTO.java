package com.aeon.hadog.base.dto.diary;

import com.aeon.hadog.domain.EmotionTrack;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiaryDTO {

    private Long diaryId;
    @NotNull
    private Long emotionTrackId;

    private LocalDate diaryDate;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

}
