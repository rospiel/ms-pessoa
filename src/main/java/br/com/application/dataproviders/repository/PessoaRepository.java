package br.com.application.dataproviders.repository;

import br.com.application.dataproviders.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByUuid(String uuid);
}
