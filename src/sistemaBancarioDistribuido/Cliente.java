import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@ToString
@NoArgsConstructor
public final class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String middleName;
	@NotBlank
	private String lastName;
	@Email
	private String email = "";
	@CPF
	@NotBlank
	private String cpf;
	@OneToOne
	private Conta conta;
	@Column(unique = true)
	private String login = "";
	@Length(min = 6)
	private String senha = "";


	public Cliente(String firstName, String middleName, String lastName, String email, String cpf, String login,String senha) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.cpf = cpf;
		this.login = login;
		this.senha = senha;
	}

	public boolean temLogin() {
		return (!login.isEmpty() && !senha.isEmpty());
	}
}
