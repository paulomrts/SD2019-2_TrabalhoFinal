package br.com.mitobank.mitobank.repositorio;

import br.com.mitobank.mitobank.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenciaRepositorio extends JpaRepository<Agencia, Long> {
}
