package br.com.application.core;

import br.com.application.core.dtos.UsuarioAutenticadoDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UsuarioAutenticadoDto usuario = ((UsuarioAutenticadoDto) user.getDetails());
        return Optional.of(usuario.getEmail());
    }
}
