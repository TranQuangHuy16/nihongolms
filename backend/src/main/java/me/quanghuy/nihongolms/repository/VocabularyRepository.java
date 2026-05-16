package me.quanghuy.nihongolms.repository;

import me.quanghuy.nihongolms.domain.vocabulary.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VocabularyRepository extends JpaRepository<Vocabulary, UUID> {
    List<Vocabulary> findByUser_IdAndDeletedFalse(UUID id);
}