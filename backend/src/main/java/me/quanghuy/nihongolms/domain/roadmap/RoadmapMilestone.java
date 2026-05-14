package me.quanghuy.nihongolms.domain.roadmap;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roadmap_milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapMilestone extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(nullable = false, length = 200)
    private String title; // VD: Hoàn thành Minna no Nihongo Chương 1-10

    private int orderIndex; // Thứ tự Kanban column

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private RoadmapStatus status = RoadmapStatus.NOT_STARTED;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<RoadmapTask> tasks = new ArrayList<>();
}
