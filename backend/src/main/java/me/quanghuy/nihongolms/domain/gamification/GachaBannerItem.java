package me.quanghuy.nihongolms.domain.gamification;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

/**
 * Bảng trung gian: Item nào nằm trong Banner nào, với drop rate riêng.
 * Tổng dropRate của tất cả items trong 1 banner phải = 1.0
 */
@Entity
@Table(name = "gacha_banner_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GachaBannerItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", nullable = false)
    private GachaBanner banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private GachaItem item;

    /**
     * Tỷ lệ rơi cụ thể trong banner này (0.0 - 1.0).
     * Có thể khác với defaultDropRate của rarity.
     */
    private double dropRate;
}
