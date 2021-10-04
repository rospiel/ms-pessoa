package br.com.application.entrypoints.mapper;

import br.com.application.dataproviders.entities.Pessoa;
import br.com.application.entrypoints.dtos.PessoaResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class PessoaMapper {

    public PessoaResponse mapPessoaResponse(Pessoa entidade) {
        PessoaResponse response = new PessoaResponse();

        if (isNull(entidade)) {
            return response;
        }

        response.setNome(entidade.getNome());
        response.setUuid(entidade.getUuid());
        response.setSalarioDia(entidade.getSalarioDia());
        return response;
    }
}
