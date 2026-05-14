package me.quanghuy.nihongolms.domain.gamification;

import jakarta.persistence.*;
import lombok.*;
import me.quanghuy.nihongolms.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gacha_banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GachaBanner extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name; // VD: "N2 Champion Banner"

    @Column(columnDefinition = "TEXT")
    private String description;

    private int costPerPull; // Gem cost cho 1 lần pull

    @Builder.Default
    private boolean active = true;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "banner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GachaBannerItem> bannerItems = new ArrayList<>();
}
