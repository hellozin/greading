package org.greading.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public EntryPointUnauthorizedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("content-type", "application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(objectMapper.writeValueAsString("FORBIDDEN"));
//                ApiResponse.ERROR(new ApiError(authException.getMessage(), HttpStatus.UNAUTHORIZED))));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
