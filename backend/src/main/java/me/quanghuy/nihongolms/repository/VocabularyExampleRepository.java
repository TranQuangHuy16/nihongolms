package me.quanghuy.nihongolms.repository;

import me.quanghuy.nihongolms.domain.vocabulary.VocabularyExample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VocabularyExampleRepository extends JpaRepository<VocabularyExample, UUID> {
}