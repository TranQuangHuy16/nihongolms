package me.quanghuy.nihongolms.domain.quiz;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    /**
     * Bài đọc hiểu chứa câu hỏi này (nullable).
     * Chỉ có giá trị khi questionType = READING.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passage_id")
    private ReadingPassage passage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    /**
     * Giải thích tại sao đáp án đúng là đúng (hiển thị sau khi trả lời).
     */
    @Column(columnDefinition = "TEXT")
    private String explanation;

    private int orderIndex;

    /**
     * Danh sách 4 đáp án (A, B, C, D).
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("label ASC")
    @Builder.Default
    private List<QuizOption> options = new ArrayList<>();
}
