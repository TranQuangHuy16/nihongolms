package me.quanghuy.nihongolms.domain.quizroom;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.domain.vocabulary.JlptLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Phòng quiz thi đấu real-time (WebSocket).
 * Host tạo phòng với mã code, mời bạn bè vào thi đấu.
 */
@Entity
@Table(name = "quiz_rooms", indexes = {
        @Index(name = "idx_quiz_room_code", columnList = "room_code", unique = true),
        @Index(name = "idx_quiz_room_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizRoom extends BaseEntity {

    @Column(name = "room_code", nullable = false, unique = true, length = 8)
    private String roomCode; // Mã phòng 8 ký tự

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User host; // Người tạo phòng

    @Column(length = 200)
    private String title;

    @Builder.Default
    private int maxParticipants = 10;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    @Builder.Default
    private QuizRoomStatus status = QuizRoomStatus.WAITING;

    @Enumerated(EnumType.STRING)
    @Column(name = "jlpt_level", length = 5)
    private JlptLevel jlptLevel;

    private int questionCount;

    @Builder.Default
    private int timeLimitSeconds = 30; // Thời gian mỗi câu (giây)

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<QuizRoomParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<QuizRoomQuestion> questions = new ArrayList<>();
}
