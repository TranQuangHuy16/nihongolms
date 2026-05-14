package me.quanghuy.nihongolms.domain.grammar;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.domain.vocabulary.JlptLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grammar_points", indexes = {
        @Index(name = "idx_grammar_user", columnList = "user_id"),
        @Index(name = "idx_grammar_jlpt", columnList = "jlpt_level")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrammarPoint extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title; // VD: ～ざるを得ない

    @Column(length = 500)
    private String structure; // VD: Verb(ない形-ない) + ざるを得ない

    @Column(columnDefinition = "TEXT")
    private String meaning; // Nghĩa tiếng Việt

    @Enumerated(EnumType.STRING)
    @Column(name = "jlpt_level", length = 5)
    private JlptLevel jlptLevel;

    @Column(columnDefinition = "TEXT")
    private String notes; // Ghi chú thêm (Markdown supported)

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "grammarPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GrammarExample> examples = new ArrayList<>();
}
