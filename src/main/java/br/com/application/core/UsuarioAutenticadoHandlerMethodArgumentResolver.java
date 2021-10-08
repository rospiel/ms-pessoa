package br.com.application.core;

import br.com.application.core.dtos.UsuarioAutenticadoDto;
import br.com.application.usecase.BuscarUsuarioAutenticadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UsuarioAutenticadoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final BuscarUsuarioAutenticadoUseCase buscarUsuarioAutenticadoUseCase;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return nonNull(methodParameter) &&
                methodParameter.hasParameterAnnotation(UsuarioAutenticado.class) &&
                UsuarioAutenticadoDto.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        if (isNull(nativeWebRequest)) {
            return UsuarioAutenticadoDto.builder().build();
        }

        return buscarUsuarioAutenticadoUseCase
                .buscar((HttpServletRequest) nativeWebRequest.getNativeRequest());
    }
}
