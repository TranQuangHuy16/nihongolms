package me.quanghuy.nihongolms.domain.grammar;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "grammar_examples")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrammarExample extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_point_id", nullable = false)
    private GrammarPoint grammarPoint;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentence;

    @Column(columnDefinition = "TEXT")
    private String sentenceReading;

    @Column(columnDefinition = "TEXT")
    private String sentenceMeaning;
}
