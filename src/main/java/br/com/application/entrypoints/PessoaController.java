package br.com.application.entrypoints;

import br.com.application.core.UsuarioAutenticado;
import br.com.application.core.dtos.UsuarioAutenticadoDto;
import br.com.application.entrypoints.dtos.PessoaRequest;
import br.com.application.entrypoints.dtos.PessoaResponse;
import br.com.application.usecase.BuscarPessoaUseCase;
import br.com.application.usecase.IncluirPessoaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pessoas", produces = APPLICATION_JSON_VALUE)
public class PessoaController {

    private final BuscarPessoaUseCase buscarPessoaUseCase;
    private final IncluirPessoaUseCase incluirPessoaUseCase;

    @GetMapping
    @ResponseStatus(OK)
    public List<PessoaResponse> buscarTodas(@UsuarioAutenticado UsuarioAutenticadoDto usuario) {
        return buscarPessoaUseCase.buscarTodasPessoas();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(OK)
    public PessoaResponse buscarPeloUuid(@PathVariable String uuid) {
        return buscarPessoaUseCase.buscarPeloUuid(uuid);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void incluir(@RequestBody @Valid PessoaRequest request) {
        incluirPessoaUseCase.incluir(request);
    }

}
