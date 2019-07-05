package br.com.mitobank.mitobank.repositorio;

import br.com.mitobank.mitobank.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepositorio extends JpaRepository<Banco, Long> {
}
