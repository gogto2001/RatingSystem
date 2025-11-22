package com.example.ratingsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private Integer rating; // 1–5 ან 1–10, როგორც შენთან არის შეთანხმებული

    // ვინ დატოვა კომენტარი (ანონიმიც შეიძლება იყოს, უბრალოდ ID ვინმეს მიენიჭოს)
    @Column(nullable = false)
    private Long authorId;

    // ვისთვის არის კომენტარი — სელერი
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private boolean approved = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
