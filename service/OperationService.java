package br.com.mitobank.mitobank.service;

import br.com.mitobank.mitobank.model.Historico;
import br.com.mitobank.mitobank.repositorio.ClienteRepositorio;
import br.com.mitobank.mitobank.repositorio.ContaRepositorio;
import br.com.mitobank.mitobank.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static br.com.mitobank.mitobank.utils.Constantes.*;

@Service
public final class OperationService implements Operation {
	private final ContaRepositorio contaRepositorio;
	private final ClienteRepositorio clienteRepositorio;
	private final MailService mailService;
	private final Loginservice loginservice;
	private final static boolean enviarEmail = true;

	@Autowired
	public OperationService(final ContaRepositorio contaRepositorio, final ClienteRepositorio clienteRepositorio, final MailService mailService, Loginservice loginservice) {
		this.contaRepositorio = contaRepositorio;
		this.clienteRepositorio = clienteRepositorio;
		this.mailService = mailService;
		this.loginservice = loginservice;
	}

	@Override
	public Map<String, String> sacar(final String login, final Long numeroConta, final Integer digitoConta, final String senhaConta, final BigDecimal valor) {
		final var conta = contaRepositorio.findByIdAndDigito(numeroConta, digitoConta);
		final var subject = "Tentativa de saque em conta";
		if (!loginservice.isLogged(login)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Usuário não logado!");
			return message;
		}
		if (conta == null) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Conta inexistente!");
			message.put(SUBJECT, subject);
			return message;
		}

		if (!conta.getSenha().equals(senhaConta)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Dados incorretos");
			message.put(SUBJECT, subject);
			return message;
		}

		final var cliente = clienteRepositorio.findByConta(conta);
		final var maiorQCem = valor.compareTo(BigDecimal.TEN.multiply(BigDecimal.TEN)) > 0;
		final var saldoInsuficiente = conta.getSaldo().compareTo(valor) < 0;

		if (maiorQCem && saldoInsuficiente && enviarEmail) {
			var msg = "Caro " +
					cliente.getFirstName() +
					"\n" +
					"Houve uma tentativa de saque no valor de R$ " +
					valor +
					", porém foi bloqueada devido saldo insuficiente. Para mais detalhes consulte seu gerente!";


			final var body = new Message(false, msg, "Saque em conta");
			mailService.sendNotification(body, cliente);

		} else if (maiorQCem && enviarEmail) {

			var msg = "Caro " +
					cliente.getFirstName() +
					"\n" +
					"Houve um saque no valor de R$ " +
					valor +
					", para mais detalhes consulte seu gerente!";

			final var body = new Message(true, msg, "Saque em conta");
			mailService.sendNotification(body, cliente);
		}


		if (saldoInsuficiente) {
			conta.addHistorico(new Historico(LocalDateTime.now(), SAQUE, conta.getSaldo(), conta.getSaldo(), false, "Saldo insuficiente"));
			contaRepositorio.save(conta);

			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Saldo insuficiente");
			message.put(SUBJECT, subject);
			return message;
		}

		final var oldSaldo = conta.getSaldo();
		conta.setSaldo(oldSaldo.subtract(valor));
		conta.addHistorico(new Historico(LocalDateTime.now(), SAQUE, oldSaldo, conta.getSaldo(), true, ""));
		contaRepositorio.save(conta);
		var message = new HashMap<String, String>();
		message.put(SUCCESS, "true");
		message.put(MESSAGE, "Saque realizado!\nSaldo:" + conta.getSaldo());
		message.put(SUBJECT, subject);
		return message;
	}

	@Override
	public Map<String, String> depositar(final String login, final Long numeroConta, final Integer digitoConta, final BigDecimal valor) {
		var subject = "Tentativa de Deposito em conta";
		if (!loginservice.isLogged(login)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Usuário não logado!");
			return message;
		}

		final var conta = contaRepositorio.findByIdAndDigito(numeroConta, digitoConta);

		if (conta == null) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Conta inexistente!");
			message.put(SUBJECT, subject);
			return message;
		}

		if (valor.compareTo(BigDecimal.ZERO) < 0) {
			conta.addHistorico(new Historico(LocalDateTime.now(), DEPOSITO, conta.getSaldo(), conta.getSaldo(), false, "Valor inválido"));
			contaRepositorio.save(conta);
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Valor inválido!");
			message.put(SUBJECT, subject);
			return message;
		}

		var oldSaldo = conta.getSaldo();
		conta.setSaldo(oldSaldo.add(valor));

		conta.addHistorico(new Historico(LocalDateTime.now(), DEPOSITO, oldSaldo, conta.getSaldo(), true, ""));
		contaRepositorio.save(conta);

		var message = new HashMap<String, String>();
		message.put(SUCCESS, "true");
		message.put(MESSAGE, "Depósito realizado!\nSaldo: " + conta.getSaldo());
		message.put(SUBJECT, subject);
		return message;
	}

	@Override
	public Map<String, String> transferir(final String login, final String senhaConta, final Long numeroOrigem, final Integer digitoOrigem, final Long numeroDestino, final Integer digitoDestino, final BigDecimal valor) {
		final var subject = "Tentativa de Transferência";
		if (!loginservice.isLogged(login)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Usuário não logado!");
			return message;
		}
		if (valor.compareTo(BigDecimal.ZERO) < 0) {

			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "valor deve ser positivo");
			message.put(SUBJECT, subject);
			return message;

		}

		final var contaOrigem = contaRepositorio.findByIdAndDigito(numeroOrigem, digitoOrigem);
		final var contaDestino = contaRepositorio.findByIdAndDigito(numeroDestino, digitoDestino);

		if (contaOrigem == null) {

			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Conta de origem inexistente");
			message.put(SUBJECT, subject);
			return message;

		}

		if (!contaOrigem.getSenha().equals(senhaConta)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Dados incorretos");
			message.put(SUBJECT, subject);
			return message;
		}

		if (contaDestino == null) {

			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Conta de destino inexistente");
			message.put(SUBJECT, subject);
			return message;
		}

		if (contaOrigem.getSaldo().compareTo(valor) < 0) {
			contaOrigem.addHistorico(new Historico(LocalDateTime.now(), TRANSFERENCIA, contaOrigem.getSaldo(), contaOrigem.getSaldo(), false, "Saldo insuficiente"));
			contaRepositorio.save(contaOrigem);

			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Saldo insuficiente");
			message.put(SUBJECT, subject);
			return message;

		}

		var origemSaldo = contaOrigem.getSaldo();
		var destinoSaldo = contaDestino.getSaldo();

		contaOrigem.setSaldo(origemSaldo.subtract(valor));
		contaDestino.setSaldo(destinoSaldo.add(valor));

		contaOrigem.addHistorico(new Historico(LocalDateTime.now(), TRANSFERENCIA, origemSaldo, contaOrigem.getSaldo(), true, "Transferido"));
		contaDestino.addHistorico(new Historico(LocalDateTime.now(), TRANSFERENCIA, destinoSaldo, contaDestino.getSaldo(), true, "Recebido"));

		contaRepositorio.save(contaOrigem);
		contaRepositorio.save(contaDestino);

		var message = new HashMap<String, String>();
		message.put(SUCCESS, "true");
		message.put(MESSAGE, "Transferencia efetuada!");
		message.put(SUBJECT, subject);
		return message;
	}

	@Override
	public Map<String, String> getExtrato(final String login, Long numeroConta, Integer digitoConta) {
		if (!loginservice.isLogged(login)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Usuário não logado!");
			return message;
		}
		final var conta = contaRepositorio.findByIdAndDigito(numeroConta, digitoConta);
		final var historicos = conta.getHistoricos();
		if (historicos == null) {
			return null;
		}

		var sb = new StringBuilder();
		final var init = LocalDateTime.now().minusDays(30);

		historicos.stream().filter(historico -> historico.getDeferido() && historico.getDataEHora().isAfter(init)).sorted().forEach(historico -> {
			final var dataEHora = historico.getDataEHora();
			sb.append(dataEHora.format(DateTimeFormatter.ofPattern("dd-MM-YYYY - HH:mm:ss"))).append(" | ").append(historico.getTipo()).append(" - ")
					.append("Valor movimentado: ")
					.append(historico.getSaldoDepois().subtract(historico.getSaldoAntes()));
			if (historico.getTipo().equalsIgnoreCase(TRANSFERENCIA)) {
				sb.append(" - ").append(historico.getObs());
			}
			sb.append("\n");
		});

		final var resultado = new HashMap<String, String>();
		resultado.put(SUCCESS,"true");
		resultado.put(EXTRATO, sb.toString());
		return resultado;
	}

	@Override
	public Map<String, String> getSaldo(final String login, Long numeroConta, Integer digitoConta) {
		String subject = "Consulta de saldo";
		if (!loginservice.isLogged(login)) {
			var message = new HashMap<String, String>();
			message.put(SUCCESS, "false");
			message.put(MESSAGE, "Usuário não logado!");
			message.put(SUBJECT, subject);
			return message;
		}

		final var conta = contaRepositorio.findByIdAndDigito(numeroConta, digitoConta);
		final var resultado = new HashMap<String, String>();
		resultado.put(SUCCESS,"true");
		resultado.put(SUBJECT, subject);
		resultado.put(SALDO, "Saldo: R$ " + conta.getSaldo());
		return resultado;
	}
}