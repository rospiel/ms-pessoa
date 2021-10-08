package br.com.application.usecase;

import br.com.application.dataproviders.repository.PessoaRepository;
import br.com.application.entrypoints.dtos.PessoaRequest;
import br.com.application.entrypoints.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class IncluirPessoaUseCase {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Transactional
    public void incluir(PessoaRequest request) {
        pessoaRepository.save(pessoaMapper.mapPessoa(request));
    }
}
