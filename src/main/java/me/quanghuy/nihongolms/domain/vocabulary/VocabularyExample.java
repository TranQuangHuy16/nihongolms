package me.quanghuy.nihongolms.domain.vocabulary;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "vocabulary_examples")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyExample extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    private Vocabulary vocabulary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentence; // Câu ví dụ tiếng Nhật

    @Column(columnDefinition = "TEXT")
    private String sentenceReading; // Phiên âm hiragana

    @Column(columnDefinition = "TEXT")
    private String sentenceMeaning; // Nghĩa tiếng Việt
}
