
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public final class Banco {
	public Banco(String nome) {
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;
	private String nome;
	@NotNull
	@OneToMany(
			targetEntity = Agencia.class,
			fetch = FetchType.LAZY,
			cascade = CascadeType.MERGE)
	@JoinColumn(name = "banco_id")
	@Setter(AccessLevel.NONE)
	private List<Agencia> agencias;

	public void addAgencia(Agencia agencia) {
		if (this.agencias == null) {
			this.agencias = new ArrayList<>();
		}
		this.agencias.add(agencia);
	}
}
