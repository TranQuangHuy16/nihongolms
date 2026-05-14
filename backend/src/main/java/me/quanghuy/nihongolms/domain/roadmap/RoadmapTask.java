package me.quanghuy.nihongolms.domain.roadmap;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "roadmap_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapTask extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false)
    private RoadmapMilestone milestone;

    @Column(nullable = false, length = 300)
    private String title;

    @Builder.Default
    private boolean completed = false;

    private LocalDate dueDate;

    private int orderIndex;
}
