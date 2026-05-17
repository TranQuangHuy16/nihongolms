package me.quanghuy.nihongolms.repository;

import me.quanghuy.nihongolms.domain.grammar.GrammarPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GrammarPointRepository extends JpaRepository<GrammarPoint, UUID> {
    @Query("SELECT v FROM GrammarPoint v " +
            "WHERE v.user.id = :userId AND v.deleted = false " +
            "AND (:search IS NULL OR :search = '' OR " +
            "LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.structure) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(v.meaning) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<GrammarPoint> searchGrammars(@Param("userId") UUID userId,
                                      @Param("search") String search,
                                      Pageable pageable);
}