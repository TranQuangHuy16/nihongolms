package me.quanghuy.nihongolms.dto.vocabulary;

import lombok.*;
import me.quanghuy.nihongolms.domain.vocabulary.JlptLevel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyRequest {
    private String kanji;
    private String reading; // Hiragana/Katakana
    private String sinoVietnamese; // Âm Hán Việt
    private String meaning;
    private JlptLevel jlptLevel;
    private String source; // Nguồn: manga, novel, anime...
    private List<Tags> tags;
    private List<VocabularyExampleResponse> examples;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VocabularyExampleResponse {
        private String sentence; // Câu ví dụ tiếng Nhật
        private String sentenceReading; // Phiên âm hiragana
        private String sentenceMeaning; // Nghĩa tiếng Việt
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Tags {
        private UUID id;
    }
}
