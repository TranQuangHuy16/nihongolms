package me.quanghuy.nihongolms.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private UUID userId;
    private String username;
    private String email;
    private String displayName;
    private String role;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn; // seconds
}
