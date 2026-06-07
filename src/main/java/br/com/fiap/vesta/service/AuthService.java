package br.com.fiap.vesta.service;

import br.com.fiap.vesta.dto.request.LoginRequest;
import br.com.fiap.vesta.dto.response.AuthResponse;
import br.com.fiap.vesta.repository.UsuarioRepository;
import br.com.fiap.vesta.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);
        String perfil = usuarioRepository.findByDsEmail(request.email())
            .map(u -> u.getPerfil().getNmPerfil().name())
            .orElse("OPERADOR");
        return AuthResponse.of(token, request.email(), perfil);
    }
}
