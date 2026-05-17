package me.quanghuy.nihongolms.dto.grammar;

import lombok.*;
import me.quanghuy.nihongolms.domain.vocabulary.JlptLevel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrammarResponse {
    private UUID id;
    private String title;
    private String structure;
    private String meaning;
    private JlptLevel jlptLevel;
    private String notes;
    private List<Example> examples;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Example {
        private String sentence;
        private String sentenceReading;
        private String sentenceMeaning;
    }
}
