package me.quanghuy.nihongolms.domain.gamification;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

@Entity
@Table(name = "gacha_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GachaItem extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name; // VD: "Sakura Badge", "Uma Musume Theme"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private GachaRarity rarity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private GachaItemType itemType;
}
