package me.quanghuy.nihongolms.domain.vocabulary;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Bảng trung gian Many-to-Many giữa Vocabulary và Tag.
 * Sử dụng composite key thay vì UUID để tối ưu storage.
 */
@Entity
@Table(name = "vocabulary_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(VocabularyTagId.class)
public class VocabularyTag {

    @Id
    @Column(name = "vocabulary_id")
    private UUID vocabularyId;

    @Id
    @Column(name = "tag_id")
    private UUID tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", insertable = false, updatable = false)
    private Vocabulary vocabulary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;
}
