package me.quanghuy.nihongolms.domain.gamification;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;
import me.quanghuy.nihongolms.domain.user.User;

/**
 * Thông tin gamification của user: tiền ảo, streak, v.v.
 * Mỗi user chỉ có 1 record (OneToOne).
 */
@Entity
@Table(name = "user_gamification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGamification extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Builder.Default
    private int gems = 0; // Tiền ảo hiện tại

    @Builder.Default
    private int tickets = 0; // Vé gacha

    @Builder.Default
    private long totalGemsEarned = 0L; // Tổng gem kiếm được (lifetime)

    @Builder.Default
    private int currentStreak = 0; // Streak hiện tại

    @Builder.Default
    private int longestStreak = 0; // Streak dài nhất từ trước tới nay
}
