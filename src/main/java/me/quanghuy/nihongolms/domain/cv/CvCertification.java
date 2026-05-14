package me.quanghuy.nihongolms.domain.cv;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "cv_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvCertification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_profile_id", nullable = false)
    private CvProfile cvProfile;

    @Column(length = 10)
    private String yearMonth; // 年月: "2025/07"

    @Column(nullable = false, length = 300)
    private String name; // 免許・資格: "日本語能力試験 N2 合格"

    private int orderIndex;
}
