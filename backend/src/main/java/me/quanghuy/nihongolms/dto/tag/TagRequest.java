package me.quanghuy.nihongolms.dto.tag;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagRequest {
    private String name;
    private String color;
}
