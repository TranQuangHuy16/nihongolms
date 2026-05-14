package me.quanghuy.nihongolms.domain.vocabulary;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

@Entity
@Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(name = "uk_tag_user_name", columnNames = {"user_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name; // VD: IT_Vocabulary, JLPT_N2, Uma_Musume

    @Column(length = 7)
    private String color; // Hex color: #FF5733
}
