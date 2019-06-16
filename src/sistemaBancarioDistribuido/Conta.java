package sistemaBancarioDistribuido;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *  Classe para dados da Conta Bancária
 * 
 * 
 * **/

@Entity

public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CONTAS")
	
	private Integer id;
	private String titular;
	private String numeroConta;
	private String agencia;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}	
	
}
