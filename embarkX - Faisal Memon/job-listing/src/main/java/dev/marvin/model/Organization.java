package dev.marvin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "t_organizations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {
    @Id
    @SequenceGenerator(name = "org_id_sequence", sequenceName = "org_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_id_sequence")
    private Integer id;
    @Column(unique = true, updatable = false, nullable = false)
    @Builder.Default
    private String orgId = UUID.randomUUID().toString();
    @Column(unique = true)
    private String name;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(orphanRemoval = true, mappedBy = "organization")
    Collection<Job> jobs = new HashSet<>();
}
