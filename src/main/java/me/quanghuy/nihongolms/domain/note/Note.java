package me.quanghuy.nihongolms.domain.note;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_note_user", columnList = "user_id"),
        @Index(name = "idx_note_category", columnList = "user_id, category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // Markdown content

    @Column(length = 100)
    private String category; // VD: 文法, 語彙, 面接準備

    @Builder.Default
    private boolean pinned = false;

    @Builder.Default
    private boolean deleted = false;
}
