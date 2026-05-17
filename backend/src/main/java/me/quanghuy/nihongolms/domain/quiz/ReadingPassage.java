package me.quanghuy.nihongolms.domain.quiz;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Bài đọc hiểu cho quiz dạng READING.
 * Một bài đọc chứa nhiều câu hỏi liên quan đến nội dung bài đọc.
 */
@Entity
@Table(name = "reading_passages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingPassage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(length = 300)
    private String title;

    /**
     * Nội dung bài đọc hiểu (tiếng Nhật).
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private int orderIndex;

    @OneToMany(mappedBy = "passage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<QuizQuestion> questions = new ArrayList<>();
}
