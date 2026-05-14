package me.quanghuy.nihongolms.domain.gamification;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

@Entity
@Table(name = "user_themes", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_theme", columnNames = {"user_id", "item_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTheme extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private GachaItem item;

    /**
     * Chỉ 1 theme được active tại 1 thời điểm cho mỗi user.
     * Logic enforce ở service layer.
     */
    @Builder.Default
    private boolean active = false;
}
