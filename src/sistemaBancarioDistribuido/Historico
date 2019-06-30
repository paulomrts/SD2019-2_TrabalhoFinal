
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
public final class Historico implements Comparable<Historico> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;
	@NotNull
	private LocalDateTime dataEHora;
	@NotEmpty
	private String tipo;
	@NotNull
	private BigDecimal saldoAntes;
	@NotNull
	private BigDecimal saldoDepois;
	@NotNull
	private Boolean deferido = false;
	private String obs;

	public Historico(@NotNull final LocalDateTime dataEHora,
					 @NotEmpty final String tipo,
					 @NonNull final BigDecimal saldoAntes,
					 @NonNull final BigDecimal saldoDepois,
					 @NonNull final Boolean deferido,
					 @NonNull final String obs) {
		this.dataEHora = dataEHora;
		this.tipo = tipo;
		this.saldoDepois = saldoDepois;
		this.saldoAntes = saldoAntes;
		this.deferido = deferido;
		this.obs = obs;
	}

	@Override
	public int compareTo(Historico o) {
		return this.dataEHora.compareTo(o.dataEHora);
	}
}
