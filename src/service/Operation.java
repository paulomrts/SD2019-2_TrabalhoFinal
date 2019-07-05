package br.com.mitobank.mitobank.service;

import java.math.BigDecimal;
import java.util.Map;

public interface Operation {

	Map<String, String> sacar(final String login, final Long numeroConta, final Integer digitoConta, String senhaConta, final BigDecimal valor);

	Map<String, String> depositar(final String login, final Long numeroConta, final Integer digitoConta, final BigDecimal valor);

	Map<String, String> transferir(final String login, final String senhaConta, final Long numeroOrigen, final Integer digitoOrigen, final Long numeroDestino, final Integer digitoDestino, final BigDecimal valor);

	Map<String, String> getExtrato(final String login, final Long numeroConta, final Integer digitoConta);

	Map<String, String> getSaldo(final String login, final Long numeroConta, final Integer digitoConta);

}