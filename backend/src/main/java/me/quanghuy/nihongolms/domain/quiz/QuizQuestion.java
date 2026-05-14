package me.quanghuy.nihongolms.domain.quiz;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.grammar.GrammarPoint;
import me.quanghuy.nihongolms.domain.vocabulary.Vocabulary;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_point_id")
    private GrammarPoint grammarPoint;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuizType questionType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    /**
     * JSON array chứa các lựa chọn cho trắc nghiệm.
     * VD: ["走る", "歩く", "飛ぶ", "泳ぐ"]
     */
    @Column(columnDefinition = "TEXT")
    private String options;

    private int orderIndex;
}
