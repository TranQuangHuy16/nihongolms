package me.quanghuy.nihongolms.domain.cv;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "cv_educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvEducation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_profile_id", nullable = false)
    private CvProfile cvProfile;

    @Column(length = 10)
    private String yearMonth; // 年月: "2024/03"

    @Column(columnDefinition = "TEXT")
    private String description; // 学歴: "FPT大学 ソフトウェア工学部 卒業"

    private int orderIndex;
}
