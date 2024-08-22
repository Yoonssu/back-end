package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=true)
    private String id;

    @Column(nullable=true)
    private String password;

    @Column(nullable=false)
    private String nickname;

    @Column(nullable=false)
    private String email;

    public User update(String name){
        this.name = name;
        return this;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AdoptReview> adoptReviews;

    @PreRemove
    private void removeReviews() {
        for (AdoptReview review : adoptReviews) {
            review.setUser(null);  // User와의 관계를 끊어준다.
        }
    }
}
