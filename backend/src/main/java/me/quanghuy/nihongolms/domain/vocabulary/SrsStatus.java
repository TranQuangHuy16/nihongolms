package me.quanghuy.nihongolms.domain.vocabulary;

/**
 * Trạng thái của flashcard trong hệ thống Spaced Repetition (SRS).
 *
 * NEW       - Từ mới chưa từng ôn tập
 * LEARNING  - Đang trong quá trình học (interval ngắn)
 * REVIEW    - Đã qua giai đoạn learning, đang ôn tập định kỳ
 * GRADUATED - Đã thuộc lòng (interval rất dài, ít cần ôn)
 */
public enum SrsStatus {
    NEW,
    LEARNING,
    REVIEW,
    GRADUATED
}
