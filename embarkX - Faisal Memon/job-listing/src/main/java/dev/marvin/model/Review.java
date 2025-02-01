package dev.marvin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @SequenceGenerator(name = "review_id_sequence", sequenceName = "review_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_sequence")
    private Integer id;
    @Column(unique = true, updatable = false, nullable = false)
    @Builder.Default
    private String reviewId = UUID.randomUUID().toString();
    private String comment;
    private Integer rating;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
}
