package me.quanghuy.nihongolms.domain.quizroom;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "quiz_room_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizRoomQuestion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private QuizRoom room;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    /**
     * JSON array: ["選択肢A", "選択肢B", "選択肢C", "選択肢D"]
     */
    @Column(columnDefinition = "TEXT")
    private String options;

    private int orderIndex;

    private int timeLimitSeconds;
}
