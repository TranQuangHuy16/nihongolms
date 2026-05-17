package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.domain.vocabulary.Tag;
import me.quanghuy.nihongolms.domain.vocabulary.Vocabulary;
import me.quanghuy.nihongolms.domain.vocabulary.VocabularyExample;
import me.quanghuy.nihongolms.domain.vocabulary.VocabularyTag;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyRequest;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyResponse;
import me.quanghuy.nihongolms.exception.ResourceNotFoundException;
import me.quanghuy.nihongolms.repository.TagRepository;
import me.quanghuy.nihongolms.repository.UserRepository;
import me.quanghuy.nihongolms.repository.VocabularyExampleRepository;
import me.quanghuy.nihongolms.repository.VocabularyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final VocabularyExampleRepository vocabularyExampleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public PageResponse<VocabularyResponse> get(String search, int page, int size, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase(
                "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));

        Page<Vocabulary> vocabularyPage = vocabularyRepository.searchVocabularies(
                this.getUserInfo().getId(), search, pageable);

        List<VocabularyResponse> items = vocabularyPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return PageResponse.<VocabularyResponse>builder()
                .items(items)
                .page(page + 1)
                .size(size)
                .total(vocabularyPage.getTotalElements())
                .build();
    }

    @Transactional(readOnly = true)
    public VocabularyResponse getById(UUID id) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy từ vựng"));

        return mapToResponse(vocabulary);
    }

    @Transactional
    public VocabularyResponse create(VocabularyRequest request) {
        User user = userRepository.findById(this.getUserInfo().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng"));

        Vocabulary vocabulary = Vocabulary.builder()
                .kanji(request.getKanji())
                .reading(request.getReading())
                .sinoVietnamese(request.getSinoVietnamese())
                .meaning(request.getMeaning())
                .jlptLevel(request.getJlptLevel())
                .source(request.getSource())
                .user(user)
                .build();

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

        Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

        List<VocabularyTag> vocabularyTags = new ArrayList<>();
        if (request.getTags() != null) {
            for (VocabularyRequest.Tags vocabTags : request.getTags()) {
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

    @Transactional
    public VocabularyResponse update(UUID id, VocabularyRequest request) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy từ vựng"));

        vocabulary.setKanji(request.getKanji());
        vocabulary.setReading(request.getReading());
        vocabulary.setSinoVietnamese(request.getSinoVietnamese());
        vocabulary.setMeaning(request.getMeaning());
        vocabulary.setJlptLevel(request.getJlptLevel());
        vocabulary.setSource(request.getSource());

        vocabulary.getExamples().clear();

        if (request.getExamples() != null) {
            for (VocabularyRequest.VocabularyExampleResponse vocabExample : request.getExamples()) {
                VocabularyExample example = VocabularyExample.builder()
                        .sentence(vocabExample.getSentence())
                        .sentenceReading(vocabExample.getSentenceReading())
                        .sentenceMeaning(vocabExample.getSentenceMeaning())
                        .vocabulary(vocabulary)
                        .build();

                vocabulary.getExamples().add(example);
            }
        }

        vocabulary.getVocabularyTags().clear();

        if (request.getTags() != null) {
            for (VocabularyRequest.Tags vocabTags : request.getTags()) {
                Tag tag = tagRepository.findById(vocabTags.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Không tìm thấy tag với id: " + vocabTags.getId()));

                VocabularyTag vocabularyTag = VocabularyTag.builder()
                        .vocabularyId(vocabulary.getId())
                        .tagId(tag.getId())
                        .vocabulary(vocabulary)
                        .tag(tag)
                        .build();

                vocabulary.getVocabularyTags().add(vocabularyTag);
            }
        }

        return mapToResponse(vocabularyRepository.save(vocabulary));
    }

    @Transactional
    public void delete(UUID id) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy từ vựng"));

        vocabulary.setDeleted(true);
        vocabularyRepository.save(vocabulary);
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
