package com.aeon.hadog.domain;


import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adopt_review")
public class AdoptReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable=false)
    private LocalDateTime reviewDate;

    @OneToMany(mappedBy = "adoptReview", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ReviewImage> images;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "author_name") // Ensure this matches the column name in your database
    private String authorName;

}
