package me.quanghuy.nihongolms.domain.cv;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "cv_work_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvWorkExperience extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_profile_id", nullable = false)
    private CvProfile cvProfile;

    @Column(length = 10)
    private String yearMonth; // 年月: "2025/04"

    @Column(columnDefinition = "TEXT")
    private String description; // 職歴: "株式会社〇〇 入社 エンジニア職"

    private int orderIndex;
}
