package br.com.fiap.vesta.dto.response;

public record AuthResponse(String token, String tipo, String email, String perfil) {
    public static AuthResponse of(String token, String email, String perfil) {
        return new AuthResponse(token, "Bearer", email, perfil);
    }
}
