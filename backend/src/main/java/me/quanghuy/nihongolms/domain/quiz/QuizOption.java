package me.quanghuy.nihongolms.domain.quiz;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

/**
 * Đáp án cho mỗi câu hỏi trắc nghiệm.
 * Mỗi câu hỏi có đúng 4 đáp án (A, B, C, D), trong đó chỉ 1 đáp án đúng.
 */
@Entity
@Table(name = "quiz_options", indexes = {
        @Index(name = "idx_option_question", columnList = "question_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;

    /**
     * Nội dung đáp án.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * Nhãn đáp án: A, B, C, D.
     */
    @Column(nullable = false, length = 1)
    private String label;

    /**
     * true nếu đây là đáp án đúng.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean correct = false;
}
