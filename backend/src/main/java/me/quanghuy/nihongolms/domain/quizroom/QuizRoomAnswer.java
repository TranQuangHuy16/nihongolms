package me.quanghuy.nihongolms.domain.quizroom;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "quiz_room_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizRoomAnswer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private QuizRoomParticipant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_question_id", nullable = false)
    private QuizRoomQuestion roomQuestion;

    @Column(columnDefinition = "TEXT")
    private String userAnswer;

    private boolean correct;

    /**
     * Thời gian phản hồi (ms). Ai trả lời nhanh hơn sẽ được điểm cao hơn.
     */
    private long responseTimeMs;

    /**
     * Điểm = f(correct, responseTimeMs).
     * VD: đúng + nhanh = 1000 điểm, đúng + chậm = 500 điểm, sai = 0.
     */
    private int scoreEarned;
}
