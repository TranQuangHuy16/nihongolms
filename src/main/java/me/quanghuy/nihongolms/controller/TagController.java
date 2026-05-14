package me.quanghuy.nihongolms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.api.ApiResponse;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.tag.TagRequest;
import me.quanghuy.nihongolms.dto.tag.TagResponse;
import me.quanghuy.nihongolms.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "Tag", description = "Quản lý Tag")
public class TagController {
    private final TagService tagService;

    @GetMapping()
    @Operation(
            summary = "Lấy danh sách tag",
            description = "Hỗ trợ phân trang, search theo tên, sắp xếp theo ngày tạo mới nhất/cũ nhất"
    )
    public ApiResponse<PageResponse<TagResponse>> getTags(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ApiResponse.success(tagService.getTags(search, page - 1, size, sortDir));
    }

    @PostMapping()
    public ApiResponse<TagResponse> create(@RequestBody TagRequest request) {
        return ApiResponse.success(tagService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TagResponse> update(@PathVariable UUID id, @RequestBody TagRequest request) {
        return ApiResponse.success(tagService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable UUID id) {
        tagService.delete(id);
        return ApiResponse.success("Xóa thẻ tag thành công");
    }
}
