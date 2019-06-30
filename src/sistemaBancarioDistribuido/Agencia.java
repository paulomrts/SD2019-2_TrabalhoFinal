
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@NoArgsConstructor
public final class Agencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;
	private String nome;
	@OneToMany(
			targetEntity = Cliente.class,
			fetch = FetchType.EAGER,
			cascade = CascadeType.MERGE)
	@JoinColumn(name = "agencia_id")
	private List<Cliente> clientes;

	public Agencia(String nome) {
		this.nome = nome;
	}

	public void addCliente(final Cliente cliente) {
		if (this.clientes == null) {
			this.clientes = new ArrayList<>();
		}
		this.clientes.add(cliente);
	}
}
