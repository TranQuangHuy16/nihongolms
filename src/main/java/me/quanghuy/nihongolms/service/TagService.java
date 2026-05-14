package me.quanghuy.nihongolms.service;

import lombok.RequiredArgsConstructor;
import me.quanghuy.nihongolms.core.util.SecurityUtil;
import me.quanghuy.nihongolms.domain.user.User;
import me.quanghuy.nihongolms.domain.vocabulary.Tag;
import me.quanghuy.nihongolms.dto.custom.PageResponse;
import me.quanghuy.nihongolms.dto.tag.TagRequest;
import me.quanghuy.nihongolms.dto.tag.TagResponse;
import me.quanghuy.nihongolms.exception.ResourceNotFoundException;
import me.quanghuy.nihongolms.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    /**
     * Lấy danh sách tag với phân trang, search theo name, sắp xếp theo ngày.
     *
     * @param search   Từ khóa tìm kiếm theo name (nullable)
     * @param page     Trang hiện tại (0-indexed)
     * @param size     Số lượng mỗi trang
     * @param sortDir  Hướng sắp xếp: "asc" (cũ nhất) hoặc "desc" (mới nhất, default)
     */
    public PageResponse<TagResponse> getTags(String search, int page, int size, String sortDir) {
        Sort sort = "asc".equalsIgnoreCase(sortDir)
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        UUID userId = this.getUserInfo().getId();

        Page<Tag> tagPage;
        if (StringUtils.hasText(search)) {
            tagPage = tagRepository.findByUser_IdAndNameContainingIgnoreCase(userId, search.trim(), pageable);
        } else {
            tagPage = tagRepository.findByUser_Id(userId, pageable);
        }

        return PageResponse.<TagResponse>builder()
                .items(tagPage.getContent().stream().map(this::mapToResponse).toList())
                .page(tagPage.getNumber())
                .size(tagPage.getSize())
                .total(tagPage.getTotalElements())
                .build();
    }

    public TagResponse create(TagRequest request) {
        Tag newTag = Tag.builder()
                .name(request.getName())
                .color(request.getColor())
                .user(this.getUserInfo())
                .build();

        return mapToResponse(tagRepository.save(newTag));
    }

    public TagResponse update(UUID id, TagRequest request) {
        Tag tag = tagRepository.findByIdAndUser_Id(id, this.getUserInfo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thẻ tag"));

        if (request.getName() != null) {
            tag.setName(request.getName());
        }

        if (request.getColor() != null) {
            tag.setColor(request.getColor());
        }

        return mapToResponse(tagRepository.save(tag));
    }

    public void delete(UUID id) {
        Tag tag = tagRepository.findByIdAndUser_Id(id, this.getUserInfo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thẻ tag"));

        tagRepository.delete(tag);
    }

    private User getUserInfo() {
        User user = SecurityUtil.getCurrentUser().getUser();

        return user;
    }


    private TagResponse mapToResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId().toString())
                .name(tag.getName())
                .color(tag.getColor())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }
}
