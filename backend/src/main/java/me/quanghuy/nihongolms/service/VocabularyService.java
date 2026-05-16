package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.domain.vocabulary.Tag;
import me.quanghuy.nihongolms.domain.vocabulary.Vocabulary;
import me.quanghuy.nihongolms.domain.vocabulary.VocabularyExample;
import me.quanghuy.nihongolms.domain.vocabulary.VocabularyTag;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyRequest;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyResponse;
import me.quanghuy.nihongolms.exception.ResourceNotFoundException;
import me.quanghuy.nihongolms.repository.TagRepository;
import me.quanghuy.nihongolms.repository.UserRepository;
import me.quanghuy.nihongolms.repository.VocabularyExampleRepository;
import me.quanghuy.nihongolms.repository.VocabularyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final VocabularyExampleRepository vocabularyExampleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<VocabularyResponse> get() {
        List<Vocabulary> vocabularies = vocabularyRepository.findByUser_IdAndDeletedFalse(
                this.getUserInfo().getId());

        return vocabularies.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional // Rất quan trọng để đảm bảo tính toàn vẹn dữ liệu
    public VocabularyResponse create(VocabularyRequest request) {
        // 1. Lấy thông tin User
        User user = userRepository.findById(this.getUserInfo().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng"));

        // 2. Khởi tạo Vocabulary (chưa có examples và tags)
        Vocabulary vocabulary = Vocabulary.builder()
                .kanji(request.getKanji())
                .reading(request.getReading())
                .sinoVietnamese(request.getSinoVietnamese())
                .meaning(request.getMeaning())
                .jlptLevel(request.getJlptLevel())
                .source(request.getSource())
                .user(user)
                .build();

        // 3. Xử lý Examples (Map 2 chiều)
        List<VocabularyExample> examples = new ArrayList<>();
        if (request.getExamples() != null) {
            for (VocabularyRequest.VocabularyExampleResponse vocabExample : request.getExamples()) {
                VocabularyExample example = VocabularyExample.builder()
                        .sentence(vocabExample.getSentence())
                        .sentenceReading(vocabExample.getSentenceReading())
                        .sentenceMeaning(vocabExample.getSentenceMeaning())
                        .vocabulary(vocabulary)
                        .build();
                examples.add(example);
            }
        }
        vocabulary.setExamples(examples);

        // 4. LƯU Vocabulary LẦN 1 để DB sinh ra UUID cho Vocabulary
        // (Bắt buộc phải lưu để lấy ID map vào VocabularyTag)
        Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

        // 5. Xử lý Tags thông qua bảng trung gian VocabularyTag
        List<VocabularyTag> vocabularyTags = new ArrayList<>();
        if (request.getTags() != null) {
            for (VocabularyRequest.Tags vocabTags : request.getTags()) {
                // Kiểm tra xem tag có tồn tại không
                Tag tag = tagRepository.findById(vocabTags.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Không tìm thấy tag với id: " + vocabTags.getId()));

                // Tạo bản ghi trung gian
                VocabularyTag vocabularyTag = VocabularyTag.builder()
                        .vocabularyId(savedVocabulary.getId()) // Đã có ID nhờ bước 4
                        .tagId(tag.getId())
                        .vocabulary(savedVocabulary)
                        .tag(tag)
                        .build();

                vocabularyTags.add(vocabularyTag);
            }
        }

        savedVocabulary.getVocabularyTags().addAll(vocabularyTags);
        savedVocabulary = vocabularyRepository.save(savedVocabulary);

        return mapToResponse(savedVocabulary);
    }

    private User getUserInfo() {
        User user = SecurityUtil.getCurrentUser().getUser();

        return user;
    }

    private VocabularyResponse mapToResponse(Vocabulary vocabulary) {
        return VocabularyResponse.builder()
                .id(vocabulary.getId())
                .kanji(vocabulary.getKanji())
                .reading(vocabulary.getReading())
                .sinoVietnamese(vocabulary.getSinoVietnamese())
                .meaning(vocabulary.getMeaning())
                .jlptLevel(vocabulary.getJlptLevel())
                .source(vocabulary.getSource())
                .tags(vocabulary.getVocabularyTags() != null ?
                        vocabulary.getVocabularyTags().stream()
                        .map(vocabTag -> {
                            Tag tag = vocabTag.getTag();
                            return VocabularyResponse.Tags.builder()
                                   .id(tag.getId())
                                   .name(tag.getName())
                                   .build();
                        }).toList()
                        : Collections.emptyList())
                .examples(vocabulary.getExamples() != null ?
                        vocabulary.getExamples().stream()
                        .map(example -> VocabularyResponse.VocabularyExampleResponse.builder()
                                        .sentence(example.getSentence())
                                        .sentenceReading(example.getSentenceReading())
                                        .sentenceMeaning(example.getSentenceMeaning())
                                        .build()).toList()
                        : Collections.emptyList())
                .build();
    }
}
