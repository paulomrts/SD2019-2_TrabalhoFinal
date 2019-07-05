package br.com.mitobank.mitobank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

@Data
@AllArgsConstructor

public class Account implements Serializable {
	private Long numero;
	private Integer digito;
}
