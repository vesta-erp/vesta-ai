package br.com.fiap.vesta.security;

import br.com.fiap.vesta.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByDsEmail(email)
            .filter(u -> u.isAtivo())
            .map(u -> new User(
                u.getDsEmail(),
                u.getDsSenhaHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getPerfil().getNmPerfil().name()))
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
