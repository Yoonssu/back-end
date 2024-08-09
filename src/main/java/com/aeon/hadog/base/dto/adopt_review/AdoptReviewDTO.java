package com.aeon.hadog.base.dto.adopt_review;

import com.aeon.hadog.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdoptReviewDTO {

    @NotNull
    private Long reviewId;

    @NotNull
    private LocalDateTime reviewDate;

    private List<ReviewImageDTO> images;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @NotBlank(message = "작성자 이름은 필수 입력 값입니다.")
    private String authorName;


}
