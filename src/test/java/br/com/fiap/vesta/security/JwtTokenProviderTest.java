package br.com.fiap.vesta.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private final JwtTokenProvider provider = new JwtTokenProvider(
        "test-secret-key-for-unit-tests-min-256-bits-long", 3600000L
    );

    @Test
    void generateToken_andValidate_returnsCorrectEmail() {
        UserDetails user = new User("test@vesta.gov.br", "hash",
            List.of(new SimpleGrantedAuthority("ROLE_OPERADOR")));

        String token = provider.generateToken(user);

        assertThat(provider.isTokenValid(token, user)).isTrue();
        assertThat(provider.extractUsername(token)).isEqualTo("test@vesta.gov.br");
    }

    @Test
    void expiredToken_isNotValid() throws InterruptedException {
        JwtTokenProvider shortLived = new JwtTokenProvider(
            "test-secret-key-for-unit-tests-min-256-bits-long", 1L
        );
        UserDetails user = new User("x@x.com", "h", List.of());
        String token = shortLived.generateToken(user);
        Thread.sleep(10);
        assertThat(shortLived.isTokenValid(token, user)).isFalse();
    }
}
