package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String token;
    private String mensaje;
    private boolean success;

    public static AuthResponse success(String token, String mensaje) {
        return AuthResponse.builder()
                .token(token)
                .mensaje(mensaje)
                .success(true)
                .build();
    }

    public static AuthResponse error(String mensaje) {
        return AuthResponse.builder()
                .mensaje(mensaje)
                .success(false)
                .build();
    }
}