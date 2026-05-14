package me.quanghuy.nihongolms.domain.vocabulary;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Composite key cho bảng vocabulary_tags.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VocabularyTagId implements Serializable {
    private UUID vocabularyId;
    private UUID tagId;
}
