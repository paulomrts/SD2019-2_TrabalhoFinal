package br.com.mitobank.mitobank.repositorio;

import br.com.mitobank.mitobank.model.Cliente;
import br.com.mitobank.mitobank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
	Cliente findByConta(final Conta conta);

	Cliente findByLogin(@Param("login") String login);
}
