package me.quanghuy.nihongolms.domain.vocabulary;

import lombok.Getter;

/**
 * Đánh giá của user khi lật flashcard (SM-2 algorithm).
 *
 * AGAIN (0) - Quên hoàn toàn, cần ôn lại ngay
 * HARD  (1) - Nhớ mang máng, khó nhớ
 * GOOD  (2) - Nhớ được, bình thường
 * EASY  (3) - Nhớ rất rõ, dễ dàng
 */
@Getter
public enum ReviewRating {
    AGAIN(0),
    HARD(1),
    GOOD(2),
    EASY(3);

    private final int quality;

    ReviewRating(int quality) {
        this.quality = quality;
    }
}
