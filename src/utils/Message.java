package br.com.mitobank.mitobank.utils;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public final class Message implements Serializable {
	private Boolean success ;
	private String message;
	private String subject;
}
