package me.quanghuy.nihongolms.domain.vocabulary;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vocabularies", indexes = {
        @Index(name = "idx_vocab_user", columnList = "user_id"),
        @Index(name = "idx_vocab_kanji", columnList = "kanji"),
        @Index(name = "idx_vocab_jlpt", columnList = "jlpt_level")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vocabulary extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String kanji;

    @Column(nullable = false, length = 200)
    private String reading; // Hiragana/Katakana

    @Column(length = 200)
    private String sinoVietnamese; // Âm Hán Việt

    @Column(nullable = false, columnDefinition = "TEXT")
    private String meaning;

    @Enumerated(EnumType.STRING)
    @Column(name = "jlpt_level", length = 5)
    private JlptLevel jlptLevel;

    @Column(length = 200)
    private String source; // Nguồn: manga, novel, anime...

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VocabularyExample> examples = new ArrayList<>();

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VocabularyTag> vocabularyTags = new ArrayList<>();
}
