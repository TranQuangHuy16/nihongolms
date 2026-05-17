package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.grammar.GrammarRequest;
import me.quanghuy.nihongolms.dto.grammar.GrammarResponse;
import me.quanghuy.nihongolms.service.GrammarService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammars")
@RequiredArgsConstructor
@Tag(name = "Grammar", description = "Grammar API")
public class GrammarController {
    private final GrammarService grammarService;

    @GetMapping()
    public ApiResponse<PageResponse<GrammarResponse>> get(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ApiResponse.success(grammarService.get(search, page - 1, size, sortDir));
    }

    @GetMapping("/{id}")
    public ApiResponse<GrammarResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(grammarService.getById(id));
    }

    @PostMapping()
    public ApiResponse<GrammarResponse> create(@Valid @RequestBody GrammarRequest request) {
        return ApiResponse.success(grammarService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<GrammarResponse> update(@PathVariable UUID id, @Valid @RequestBody GrammarRequest request) {
        return ApiResponse.success(grammarService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable UUID id) {
        grammarService.delete(id);
        return ApiResponse.success("Xóa thành công");
    }
}
