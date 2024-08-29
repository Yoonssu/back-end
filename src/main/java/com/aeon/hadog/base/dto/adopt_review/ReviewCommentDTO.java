package com.aeon.hadog.base.dto.adopt_review;

import com.aeon.hadog.domain.User;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCommentDTO {

    @NotNull
    private Long cmtId;

    @NotNull
    private String content; // 댓글 내용

    private String userId;

    @NotNull
    private LocalDateTime cmtDate; // 댓글 작성일


    private Long parentCommentId; // 부모 댓글 ID
}
