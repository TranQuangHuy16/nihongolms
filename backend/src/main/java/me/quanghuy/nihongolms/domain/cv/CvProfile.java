package me.quanghuy.nihongolms.domain.cv;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CV tiếng Nhật (履歴書 - Rirekisho).
 * Lưu trữ thông tin cá nhân theo format chuẩn Nhật Bản.
 */
@Entity
@Table(name = "cv_profiles", indexes = {
        @Index(name = "idx_cv_user", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvProfile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String fullNameKanji; // 氏名

    @Column(length = 100)
    private String fullNameFurigana; // ふりがな

    private LocalDate dateOfBirth; // 生年月日

    @Column(length = 10)
    private String gender; // 性別

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 500)
    private String address; // 住所 (現住所)

    @Column(length = 100)
    private String nearestStation; // 最寄り駅

    @Column(columnDefinition = "TEXT")
    private String selfPr; // 自己PR

    @Column(columnDefinition = "TEXT")
    private String motivation; // 志望動機

    @Column(length = 200)
    private String commute; // 通勤時間

    private Integer dependents; // 扶養家族数 (配偶者を除く)

    @Column(length = 500)
    private String photoUrl; // 証明写真

    @Builder.Default
    private int version = 1; // Phiên bản CV

    @OneToMany(mappedBy = "cvProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<CvEducation> educations = new ArrayList<>();

    @OneToMany(mappedBy = "cvProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<CvWorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "cvProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<CvCertification> certifications = new ArrayList<>();
}
