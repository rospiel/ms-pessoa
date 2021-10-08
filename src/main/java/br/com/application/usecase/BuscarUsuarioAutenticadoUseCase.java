package br.com.application.usecase;

import br.com.application.core.dtos.UsuarioAutenticadoDto;
import br.com.application.core.properties.PessoaProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor
@Service
@Slf4j
public class BuscarUsuarioAutenticadoUseCase {

    private final PessoaProperties pessoaProperties;

    public UsuarioAutenticadoDto buscar(HttpServletRequest httpServletRequest) {
        String token = buscarToken(httpServletRequest);
        PublicKey publicKey = criarChaveAcesso();
        Claims claims = buscarClaims(token, publicKey);
        UsuarioAutenticadoDto usuario = gerarUsuarioAutenticadoDto(claims);
        informarSecurityContext(usuario);
        return usuario;
    }

    private void informarSecurityContext(UsuarioAutenticadoDto usuarioAutenticadoDto) {

        if (isNull(usuarioAutenticadoDto)) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return usuarioAutenticadoDto;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException { }

            @Override
            public String getName() {
                return null;
            }
        });
    }

    private UsuarioAutenticadoDto gerarUsuarioAutenticadoDto(Claims claims) {
        UsuarioAutenticadoDto.UsuarioAutenticadoDtoBuilder usuario = UsuarioAutenticadoDto.builder();
        if (isNull(claims)) {
            return usuario.build();
        }

        return usuario.nome((String) claims.get("name"))
                .email((String) claims.get("email"))
                .build();
    }

    private Claims buscarClaims(String token, PublicKey publicKey) {
        if (isBlank(token) || isNull(publicKey)) {
            return null;
        }

        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String buscarToken(HttpServletRequest httpServletRequest) {
        if (isNull(httpServletRequest)) {
            return null;
        }

        String token = httpServletRequest.getHeader("Authorization");
        return isBlank(token) ? null : token.replace("Bearer ", EMPTY);
    }

    private PublicKey criarChaveAcesso() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(pessoaProperties.getToken().getModeloAlgoritmo());
            X509EncodedKeySpec x509EncodedKeySpec =
                    new X509EncodedKeySpec(Base64.getDecoder().decode(pessoaProperties.getToken().getChavePublica()));

            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            String mensagemErro = "Erro ao buscar algoritmo usado no token jwt. Algoritmo.: %s";
            log.error(format(mensagemErro, pessoaProperties.getToken().getModeloAlgoritmo()), e);
        } catch (InvalidKeySpecException e) {
            String mensagemErro = "Chave pública inválida para o mecanismo de construção da PublicKey.";
            log.error(mensagemErro, e);
        }

        return null;
    }
}
