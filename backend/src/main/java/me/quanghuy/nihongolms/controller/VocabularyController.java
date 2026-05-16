package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyRequest;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyResponse;
import me.quanghuy.nihongolms.service.VocabularyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vocabularies")
@RequiredArgsConstructor
@Tag(name = "Vocabulary", description = "Vocabulary API")
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping
    public ApiResponse<List<VocabularyResponse>> get() {
        return ApiResponse.success(vocabularyService.get());
    }

    @PostMapping()
    public ApiResponse<VocabularyResponse> create(@Valid @RequestBody VocabularyRequest request) {
        return ApiResponse.success(vocabularyService.create(request));
    }
}
