package me.quanghuy.nihongolms.repository;

import me.quanghuy.nihongolms.domain.vocabulary.Vocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VocabularyRepository extends JpaRepository<Vocabulary, UUID> {
    List<Vocabulary> findByUser_IdAndDeletedFalse(UUID id);

    @Query("SELECT v FROM Vocabulary v " +
            "WHERE v.user.id = :userId AND v.deleted = false " +
            "AND (:search IS NULL OR :search = '' OR " +
            "LOWER(v.kanji) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.reading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.meaning) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.sinoVietnamese) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Vocabulary> searchVocabularies(@Param("userId") UUID userId,
                                        @Param("search") String search,
                                        Pageable pageable);
}