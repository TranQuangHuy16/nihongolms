package me.quanghuy.nihongolms.dto.custom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(name = "PageResponse", description = "Paginated response body")
public class PageResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long total;
}

