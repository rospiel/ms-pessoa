package br.com.application.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("ms-pessoa")
public class PessoaProperties {

    private PessoaProperties.Token token = new PessoaProperties.Token();

    @Getter
    @Setter
    public class Token {
        private String chavePublica;
        private String modeloAlgoritmo;
    }
}
