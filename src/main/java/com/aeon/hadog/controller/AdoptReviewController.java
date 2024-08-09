package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
import com.aeon.hadog.base.dto.adopt_review.ReviewCommentDTO;
import com.aeon.hadog.base.dto.adopt_review.ReviewImageDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.repository.UserRepository;
import com.aeon.hadog.repository.ReviewCommentRepository;
import com.aeon.hadog.domain.AdoptReview;
import com.aeon.hadog.domain.ReviewComment;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.UserRepository;
import com.aeon.hadog.service.AdoptReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class AdoptReviewController {
    private final UserRepository userRepository;

    private final AdoptReviewService adoptReviewService;
    //대댓글
    private final ReviewCommentRepository reviewCommentRepository;


    @PostMapping
    public ResponseEntity<ResponseDTO> createReview(@AuthenticationPrincipal String user,
                                                    @RequestPart AdoptReviewDTO reviewDTO,
                                                    @RequestPart List<MultipartFile> images) {
        try {
            User user1 = userRepository.findById(user).orElseThrow();
            AdoptReview review = AdoptReview.builder()
                    .user(user1)
                    .reviewDate(LocalDateTime.now())
                    .content(reviewDTO.getContent())
                    .authorName(user1.getName()) // 작성자 이름 설정
                    .build();

            AdoptReview savedReview = adoptReviewService.saveReview(review, images);

            AdoptReviewDTO responseDTO = AdoptReviewDTO.builder()
                    .reviewId(savedReview.getReviewId())
                    .reviewDate(savedReview.getReviewDate())
                    .content(savedReview.getContent())
                    .authorName(savedReview.getAuthorName()) // DTO에 작성자 이름 포함
                    .images(savedReview.getImages().stream().map(image ->
                            ReviewImageDTO.builder()
                                    .imageId(image.getImageId())
                                    .fileName(image.getFileName())
                                    .build()
                    ).collect(Collectors.toList()))
                    .build();

            return ResponseEntity.ok(new ResponseDTO<>(200, true, "입양후기 등록 성공", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "입양후기 등록 실패: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<AdoptReviewDTO>> getReview(@PathVariable Long id) {
        return adoptReviewService.findById(id)
                .map(review -> {
                    AdoptReviewDTO responseDTO = AdoptReviewDTO.builder()
                            .reviewId(review.getReviewId())
                            .reviewDate(review.getReviewDate())
                            .content(review.getContent())
                            .authorName(review.getAuthorName()) // DTO에 작성자 이름 포함
                            .images(review.getImages().stream().map(image ->
                                    ReviewImageDTO.builder()
                                            .imageId(image.getImageId())
                                            .fileName(image.getFileName())
                                            .build()
                            ).collect(Collectors.toList()))
                            .build();

                    return ResponseEntity.ok(new ResponseDTO<>(200, true, "입양후기 조회 성공", responseDTO));
                })
                .orElseGet(() -> ResponseEntity.ok(new ResponseDTO<>(400, false, "입양후기 조회 실패: 후기 없음", null)));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllReviews() {
        List<AdoptReviewDTO> reviews = adoptReviewService.findAll().stream()
                .map(review -> AdoptReviewDTO.builder()
                        .reviewId(review.getReviewId())
                        .reviewDate(review.getReviewDate())
                        .content(review.getContent())
                        .authorName(review.getAuthorName()) // DTO에 작성자 이름 포함
                        .images(review.getImages().stream().map(image ->
                                ReviewImageDTO.builder()
                                        .imageId(image.getImageId())
                                        .fileName(image.getFileName())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDTO<>(200, true, "입양후기 목록 조회 성공", reviews));
    }

    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<ResponseDTO> addComment(@PathVariable Long reviewId,
                                                                    @RequestBody ReviewCommentDTO commentDTO,
                                                                    @AuthenticationPrincipal String user) {
        try {
            User user1 = userRepository.findById(user).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

            ReviewComment comment = ReviewComment.builder()
                    .adoptReview(AdoptReview.builder().reviewId(reviewId).build())
                    .content(commentDTO.getContent())
                    .cmtDate(LocalDateTime.now())
                    .parentComment(commentDTO.getParentCommentId() != null ? ReviewComment.builder().cmtId(commentDTO.getParentCommentId()).build() : null)
                    .user(user1)
                    .build();

            ReviewComment savedComment = adoptReviewService.saveComment(comment);

            ReviewCommentDTO responseDTO = ReviewCommentDTO.builder()
                    .cmtId(savedComment.getCmtId())
                    .content(savedComment.getContent())
                    .cmtDate(savedComment.getCmtDate())
                    .parentCommentId(savedComment.getParentComment() != null ? savedComment.getParentComment().getCmtId() : null)
                    .build();

            return ResponseEntity.ok(new ResponseDTO<>(200, true, "댓글 등록 성공", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "댓글 등록 실패: " + e.getMessage(), null));
        }
    }


    @GetMapping("/{reviewId}/comments")
    public ResponseEntity<ResponseDTO> getComments(@PathVariable Long reviewId) {
        List<ReviewCommentDTO> comments = adoptReviewService.findAllCommentsByReviewId(reviewId).stream()
                .map(comment -> ReviewCommentDTO.builder()
                        .cmtId(comment.getCmtId())
                        .content(comment.getContent())
                        .cmtDate(comment.getCmtDate())
                        .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCmtId() : null)
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDTO<>(200, true, "댓글 목록 조회 성공", comments));
    }



    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<ResponseDTO> addReply(@PathVariable Long commentId,
                                                                  @RequestBody ReviewCommentDTO replyDTO,
                                                                  @AuthenticationPrincipal String user) {
        try {
            // 부모 댓글을 찾음
            ReviewComment parentComment = reviewCommentRepository.findById(commentId)
                    .orElseThrow(() -> new Exception("부모 댓글을 찾을 수 없습니다."));

            // 현재 사용자의 ID를 기반으로 사용자 정보를 가져옴
            User user1 = userRepository.findById(user)
                    .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

            // 대댓글을 생성하고 저장
            ReviewComment reply = ReviewComment.builder()
                    .adoptReview(parentComment.getAdoptReview()) // 부모 댓글의 AdoptReview 사용
                    .content(replyDTO.getContent())
                    .cmtDate(LocalDateTime.now())
                    .parentComment(parentComment) // 부모 댓글 설정
                    .user(user1) // 대댓글 작성자 설정
                    .build();

            ReviewComment savedReply = adoptReviewService.saveComment(reply);

            // 저장된 대댓글 정보를 DTO로 변환하여 반환
            ReviewCommentDTO responseDTO = ReviewCommentDTO.builder()
                    .cmtId(savedReply.getCmtId())
                    .content(savedReply.getContent())
                    .cmtDate(savedReply.getCmtDate())
                    .parentCommentId(savedReply.getParentComment() != null ? savedReply.getParentComment().getCmtId() : null)
                    .build();

            return ResponseEntity.ok(new ResponseDTO<>(200, true, "대댓글 등록 성공", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "대댓글 등록 실패: " + e.getMessage(), null));
        }
    }



    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<ResponseDTO> getReplies(@PathVariable Long commentId) {
        List<ReviewCommentDTO> replies = adoptReviewService.findRepliesByParentCommentId(commentId).stream()
                .map(reply -> ReviewCommentDTO.builder()
                        .cmtId(reply.getCmtId())
                        .content(reply.getContent())
                        .cmtDate(reply.getCmtDate())
                        .parentCommentId(reply.getParentComment() != null ? reply.getParentComment().getCmtId() : null)
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDTO<>(200, true, "대댓글 목록 조회 성공", replies));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable Long commentId) {
        try {
            // 대댓글이 있는지 확인하고 모두 삭제
            List<ReviewComment> replies = reviewCommentRepository.findByParentCommentCmtId(commentId);
            for (ReviewComment reply : replies) {
                adoptReviewService.deleteComment(reply.getCmtId());
            }

            // 부모 댓글 삭제
            adoptReviewService.deleteComment(commentId);

            return ResponseEntity.ok(new ResponseDTO<>(200, true, "댓글 삭제 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "댓글 삭제 실패: " + e.getMessage(), null));
        }
    }

    // 대댓글 삭제 메서드
    @DeleteMapping("/comments/replies/{replyId}")
    public ResponseEntity<ResponseDTO> deleteReply(@PathVariable Long replyId) {
        try {
            adoptReviewService.deleteComment(replyId);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "대댓글 삭제 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "대댓글 삭제 실패: " + e.getMessage(), null));
        }
    }

}

