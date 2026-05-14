package me.quanghuy.nihongolms.repository;

import me.quanghuy.nihongolms.domain.vocabulary.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Optional<Tag> findByIdAndUser_Id(UUID id, UUID userId);

    /**
     * Tìm tag theo user, có search theo name (LIKE %keyword%) và phân trang.
     */
    Page<Tag> findByUser_IdAndNameContainingIgnoreCase(UUID userId, String name, Pageable pageable);

    /**
     * Lấy tất cả tag của user có phân trang (không search).
     */
    Page<Tag> findByUser_Id(UUID userId, Pageable pageable);
}