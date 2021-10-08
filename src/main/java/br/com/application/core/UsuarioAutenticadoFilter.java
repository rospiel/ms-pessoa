package br.com.application.core;

import br.com.application.usecase.BuscarUsuarioAutenticadoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Order(1)
@Slf4j
public class UsuarioAutenticadoFilter implements Filter {

    private final BuscarUsuarioAutenticadoUseCase buscarUsuarioAutenticadoUseCase;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            buscarUsuarioAutenticadoUseCase.buscar((HttpServletRequest) servletRequest);
        } catch (Exception e) {
            log.error("Erro ao obter o usuário autenticado pelo UsuarioAutenticadoFilter.", e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
