package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.grammar.GrammarExample;
import me.quanghuy.nihongolms.domain.grammar.GrammarPoint;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.grammar.GrammarRequest;
import me.quanghuy.nihongolms.dto.grammar.GrammarResponse;
import me.quanghuy.nihongolms.exception.ResourceNotFoundException;
import me.quanghuy.nihongolms.repository.GrammarPointRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrammarService {
    private final GrammarPointRepository grammarPointRepository;

    public PageResponse<GrammarResponse> get(String search, int page, int size, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase(
                "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));

        Page<GrammarPoint> grammarPage = grammarPointRepository.searchGrammars(
                this.getUserInfo().getId(), search, pageable);

        List<GrammarResponse> items = grammarPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return PageResponse.<GrammarResponse>builder()
                .items(items)
                .page(page + 1)
                .size(size)
                .total(grammarPage.getTotalElements())
                .build();
    }

    public GrammarResponse getById(UUID id) {
        GrammarPoint grammar = grammarPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy ngữ pháp"));

        return mapToResponse(grammar);
    }


    public GrammarResponse create(GrammarRequest request) {
        User user = this.getUserInfo();

        GrammarPoint newGrammar = GrammarPoint.builder()
                .user(user)
                .title(request.getTitle())
                .structure(request.getStructure())
                .meaning(request.getMeaning())
                .jlptLevel(request.getJlptLevel())
                .notes(request.getNotes())
                .build();

        List<GrammarExample> examples = new ArrayList<>();

        if (request.getExamples() != null) {
            for (GrammarRequest.Example grammarExample : request.getExamples()) {
                GrammarExample example = GrammarExample.builder()
                        .sentence(grammarExample.getSentence())
                        .sentenceReading(grammarExample.getSentenceReading())
                        .sentenceMeaning(grammarExample.getSentenceMeaning())
                        .grammarPoint(newGrammar)
                        .build();

                examples.add(example);
            }
        }

        newGrammar.setExamples(examples);

        return mapToResponse(grammarPointRepository.save(newGrammar));
    }

    public GrammarResponse update(UUID id, GrammarRequest request) {
        GrammarPoint grammar = grammarPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy ngữ pháp"));

        grammar.setTitle(request.getTitle());
        grammar.setStructure(request.getStructure());
        grammar.setMeaning(request.getMeaning());
        grammar.setJlptLevel(request.getJlptLevel());
        grammar.setNotes(request.getNotes());

        grammar.getExamples().clear();

        if (request.getExamples() != null) {
            for (GrammarRequest.Example grammarExample : request.getExamples()) {
                GrammarExample example = GrammarExample.builder()
                        .sentence(grammarExample.getSentence())
                        .sentenceReading(grammarExample.getSentenceReading())
                        .sentenceMeaning(grammarExample.getSentenceMeaning())
                        .grammarPoint(grammar)
                        .build();

                grammar.getExamples().add(example);
            }
        }

        return mapToResponse(grammarPointRepository.save(grammar));
    }

    public void delete(UUID id) {
        GrammarPoint grammar = grammarPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy ngữ pháp"));

        grammar.setDeleted(true);
        grammarPointRepository.save(grammar);
    }

    private User getUserInfo() {
        User user = SecurityUtil.getCurrentUser().getUser();

        return user;
    }

    private GrammarResponse mapToResponse(GrammarPoint grammar) {
        return GrammarResponse.builder()
                .id(grammar.getId())
                .title(grammar.getTitle())
                .structure(grammar.getStructure())
                .meaning(grammar.getMeaning())
                .jlptLevel(grammar.getJlptLevel())
                .notes(grammar.getNotes())
                .examples(grammar.getExamples().stream()
                        .map(example -> GrammarResponse.Example.builder()
                                .sentence(example.getSentence())
                                .sentenceReading(example.getSentenceReading())
                                .sentenceMeaning(example.getSentenceMeaning())
                                .build())
                        .toList())
                .build();
    }
}
