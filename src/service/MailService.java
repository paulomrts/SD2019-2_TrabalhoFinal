package br.com.mitobank.mitobank.service;

import br.com.mitobank.mitobank.model.Cliente;
import br.com.mitobank.mitobank.utils.Message;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class MailService {
	final static String SUFIXO = "\n\nEsse e-mail é de natureza automática, por favor não o responda!";
	final static String EMAIL = Optional.ofNullable(System.getenv("MAIL")).orElse("noreplymitobank@gmail.com");
	final static String SENHA = Optional.ofNullable(System.getenv("PASSWORD")).orElse("@Brasil001");

	public void sendNotification(final Message message, final Cliente cliente) {

		if (!cliente.getEmail().contains("@") || cliente.getEmail().isBlank()) {
			System.out.println(cliente.getFirstName() + " não possui e-mail");
			return;
		}

		Email email = EmailBuilder.startingBlank()
				.from("MitoBank", EMAIL)
				.to(cliente.getFirstName(), cliente.getEmail())
				.withSubject(message.getSubject())
				.withPlainText(message.getMessage() + SUFIXO)
				.buildEmail();

		MailerBuilder
				.withSMTPServer("smtp.gmail.com", 587, EMAIL, SENHA)
				.withTransportStrategy(TransportStrategy.SMTP_TLS)
				.buildMailer()
				.sendMail(email);
	}
}
