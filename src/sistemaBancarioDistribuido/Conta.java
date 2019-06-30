
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "Conta")
@NoArgsConstructor
public final class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;

	@Column(name = "digito")
	private Integer digito = 0;
	private BigDecimal saldo = BigDecimal.ZERO;
	@Length(min = 4, max = 4)
	private String senha;
	@OneToMany(
			targetEntity = Historico.class,
			fetch = FetchType.EAGER,
			cascade = CascadeType.MERGE)
	@JoinColumn(name = "conta_id")
	private List<Historico> historicos;

	public void addHistorico(final Historico historico) {
		this.historicos.add(historico);
	}
}
