package br.com.application.usecase;

import br.com.application.dataproviders.entities.Pessoa;
import br.com.application.dataproviders.repository.PessoaRepository;
import br.com.application.entrypoints.dtos.PessoaResponse;
import br.com.application.entrypoints.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BuscarPessoaUseCase {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public List<PessoaResponse> buscarTodasPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        if (isEmpty(pessoas)) {
            return asList(pessoaMapper.mapPessoaResponse(null));
        }

        List<PessoaResponse> response = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            response.add(pessoaMapper.mapPessoaResponse(pessoa));
        }
        return response;
    }

    public PessoaResponse buscarPeloUuid(String uuid) {
        if (isBlank(uuid)) {
            return pessoaMapper.mapPessoaResponse(null);
        }

        Pessoa pessoa = pessoaRepository.findByUuid(uuid);
        return pessoaMapper.mapPessoaResponse(pessoa);
    }
}
