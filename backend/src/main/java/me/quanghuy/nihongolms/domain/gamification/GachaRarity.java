package me.quanghuy.nihongolms.domain.gamification;

import lombok.Getter;

/**
 * Độ hiếm của item gacha với tỷ lệ rơi mặc định.
 *
 * COMMON    - 70% drop rate (★)
 * RARE      - 20% drop rate (★★)
 * EPIC      - 8%  drop rate (★★★)
 * LEGENDARY - 2%  drop rate (★★★★)
 */
@Getter
public enum GachaRarity {
    COMMON(0.70),
    RARE(0.20),
    EPIC(0.08),
    LEGENDARY(0.02);

    private final double defaultDropRate;

    GachaRarity(double defaultDropRate) {
        this.defaultDropRate = defaultDropRate;
    }
}
