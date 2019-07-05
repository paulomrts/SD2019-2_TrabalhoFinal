package br.com.mitobank.mitobank.service;

import java.util.HashMap;
import java.util.Map;

public interface Login {

	boolean isLogged(String login);

	Map<String, String> logar(String login, String senha);

	boolean logout(String login);

	default Map<String, String> hello() {
		var r = new HashMap<String, String>();
		r.put("Hello", "from SERVER");
		return r;
	}

}