package br.com.application.configuration;

import br.com.application.core.UsernameAuditorAware;
import br.com.application.core.UsuarioAutenticadoHandlerMethodArgumentResolver;
import br.com.application.usecase.BuscarUsuarioAutenticadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class WebConfig implements WebMvcConfigurer {

    private final BuscarUsuarioAutenticadoUseCase buscarUsuarioAutenticadoUseCase;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UsuarioAutenticadoHandlerMethodArgumentResolver(buscarUsuarioAutenticadoUseCase));
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

}
