package dev.marvin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    @Id
    @SequenceGenerator(name = "job_id_sequence", sequenceName = "job_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_id_sequence")
    private Integer id;
    @Column(unique = true, updatable = false, nullable = false)
    @Builder.Default
    private String jobId = UUID.randomUUID().toString();
    @Column(unique = true)
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "org_id_fk")
    private Organization organization;
}
