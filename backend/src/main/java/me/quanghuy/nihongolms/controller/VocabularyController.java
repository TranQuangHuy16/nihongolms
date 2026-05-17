package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyRequest;
import me.quanghuy.nihongolms.dto.vocabulary.VocabularyResponse;
import me.quanghuy.nihongolms.service.VocabularyService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vocabularies")
@RequiredArgsConstructor
@Tag(name = "Vocabulary", description = "Vocabulary API")
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping
    public ApiResponse<PageResponse<VocabularyResponse>> get(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ApiResponse.success(vocabularyService.get(search, page - 1, size, sortDir));
    }

    @GetMapping("/{id}")
    public ApiResponse<VocabularyResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(vocabularyService.getById(id));
    }

    @PostMapping()
    public ApiResponse<VocabularyResponse> create(@Valid @RequestBody VocabularyRequest request) {
        return ApiResponse.success(vocabularyService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<VocabularyResponse> update(
            @PathVariable UUID id,
            @RequestBody VocabularyRequest request) {
        return ApiResponse.success(vocabularyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable UUID id) {
        vocabularyService.delete(id);
        return ApiResponse.success("Xóa từ vựng thành công");
    }
}
