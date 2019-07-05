package br.com.mitobank.mitobank.repositorio;

import br.com.mitobank.mitobank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepositorio extends JpaRepository<Conta, Long> {
	Conta findByIdAndDigito(Long numero, Integer digito);
}
