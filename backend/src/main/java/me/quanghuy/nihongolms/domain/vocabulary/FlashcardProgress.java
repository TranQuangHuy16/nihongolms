package me.quanghuy.nihongolms.domain.vocabulary;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.grammar.GrammarPoint;
import me.quanghuy.nihongolms.domain.user.User;

import java.time.LocalDate;

/**
 * Tiến độ flashcard theo thuật toán SM-2 (Spaced Repetition System).
 *
 * Mỗi record đại diện cho tiến độ ôn tập của 1 user với 1 item (vocabulary hoặc grammar).
 * Hệ thống sử dụng easeFactor, interval, repetitions để tính nextReviewDate.
 *
 * SM-2 Formula:
 * - easeFactor = max(1.3, EF + (0.1 - (5-q) * (0.08 + (5-q) * 0.02)))
 * - interval(1) = 1 day, interval(2) = 6 days, interval(n) = interval(n-1) * EF
 */
@Entity
@Table(name = "flashcard_progress", uniqueConstraints = {
        @UniqueConstraint(name = "uk_flashcard_user_vocab", columnNames = {"user_id", "vocabulary_id"}),
        @UniqueConstraint(name = "uk_flashcard_user_grammar", columnNames = {"user_id", "grammar_point_id"})
}, indexes = {
        @Index(name = "idx_flashcard_next_review", columnList = "user_id, next_review_date"),
        @Index(name = "idx_flashcard_status", columnList = "user_id, status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashcardProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_point_id")
    private GrammarPoint grammarPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false, length = 15)
    private FlashcardContentType contentType;

    /**
     * Ease Factor (EF) - Hệ số dễ/khó. Mặc định 2.5.
     * Giá trị càng cao = từ càng dễ nhớ → interval dài hơn.
     * Không bao giờ < 1.3
     */
    @Builder.Default
    private double easeFactor = 2.5;

    /**
     * Khoảng cách (ngày) giữa các lần ôn tập.
     */
    @Builder.Default
    private int intervalDays = 0;

    /**
     * Số lần ôn tập liên tiếp trả lời đúng (quality >= GOOD).
     * Reset về 0 khi trả lời AGAIN.
     */
    @Builder.Default
    private int repetitions = 0;

    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;

    private LocalDate lastReviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    @Builder.Default
    private SrsStatus status = SrsStatus.NEW;
}
