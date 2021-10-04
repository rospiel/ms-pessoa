package br.com.application.entrypoints;

import br.com.application.entrypoints.dtos.PessoaResponse;
import br.com.application.usecase.BuscarPessoaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pessoas", produces = APPLICATION_JSON_VALUE)
public class PessoaController {

    private final BuscarPessoaUseCase buscarPessoaUseCase;

    @GetMapping
    @ResponseStatus(OK)
    public List<PessoaResponse> buscarTodas() {
        return buscarPessoaUseCase.buscarTodasPessoas();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(OK)
    public PessoaResponse buscarPeloUuid(@PathVariable String uuid) {
        return buscarPessoaUseCase.buscarPeloUuid(uuid);
    }

}
