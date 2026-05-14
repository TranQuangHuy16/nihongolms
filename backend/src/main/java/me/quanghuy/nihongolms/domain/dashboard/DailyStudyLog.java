package me.quanghuy.nihongolms.domain.dashboard;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

import java.time.LocalDate;

/**
 * Ghi lại hoạt động học tập mỗi ngày.
 * Dùng để tạo Heatmap (giống GitHub contribution graph) và tracking streak.
 */
@Entity
@Table(name = "daily_study_logs", uniqueConstraints = {
        @UniqueConstraint(name = "uk_study_log_user_date", columnNames = {"user_id", "study_date"})
}, indexes = {
        @Index(name = "idx_study_log_user_date", columnList = "user_id, study_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyStudyLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "study_date", nullable = false)
    private LocalDate studyDate;

    @Builder.Default
    private int cardsReviewed = 0; // Số flashcard đã ôn

    @Builder.Default
    private int newCardsLearned = 0; // Số từ mới đã học

    @Builder.Default
    private int quizzesTaken = 0; // Số quiz đã làm

    @Builder.Default
    private int studyMinutes = 0; // Tổng thời gian học (phút)

    @Builder.Default
    private int streakDay = 0; // Chuỗi ngày học liên tiếp
}
