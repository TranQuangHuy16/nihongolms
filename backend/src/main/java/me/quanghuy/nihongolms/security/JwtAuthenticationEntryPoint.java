package me.quanghuy.nihongolms.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Xử lý khi user chưa authenticated truy cập vào resource bảo vệ.
 * Trả về 401 Unauthorized với format JSON chuẩn.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = """
                {
                    "status": 401,
                    "message": "Unauthorized: %s",
                    "data": null,
                    "errors": null
                }
                """.formatted(authException.getMessage());

        response.getWriter().write(json);
    }
}
