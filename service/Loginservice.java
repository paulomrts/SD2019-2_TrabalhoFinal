package br.com.mitobank.mitobank.service;

import br.com.mitobank.mitobank.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static br.com.mitobank.mitobank.utils.Constantes.*;

@Service
public class Loginservice implements Login {

	private final ClienteRepositorio clienteRepositorio;

	private final int tempo = 1000 * 60;

	final Map<String, Instant> mapaSessao = new HashMap<>();

	public Loginservice(ClienteRepositorio clienteRepositorio) {
		this.clienteRepositorio = clienteRepositorio;
	}

	@Override
	public boolean isLogged(String login) {
		final var instant = mapaSessao.get(login);

		if (instant == null) {
			return false;
		}

		final var tempoLogado = Duration.between(instant, Instant.now()).toMillis();

		if (tempoLogado > tempo) {
			mapaSessao.remove(login);
			return false;
		}

		mapaSessao.put(login, Instant.now());

		return true;
	}

	@Override
	public Map<String, String> logar(String login, String senha) {
		final var cliente = clienteRepositorio.findByLogin(login);

		if (cliente == null) {
			return null;
		}

		if (cliente.getSenha().equals(senha)) {
			mapaSessao.put(login, Instant.now());
			final var conta = cliente.getConta();
			var resultado = new HashMap<String, String>();
			resultado.put(LOGIN, login);
			resultado.put(NUMERO, conta.getId().toString());
			resultado.put(DIGITO, conta.getDigito().toString());
			return resultado;
		}

		return null;
	}

	@Override
	public boolean logout(String login) {
		mapaSessao.remove(login);
		return true;
	}
}