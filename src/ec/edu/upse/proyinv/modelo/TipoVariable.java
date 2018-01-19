package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tipo_variable")
@NoArgsConstructor
public class TipoVariable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_variable")
	@Getter @Setter private Long idTipoVariable;
	
	@Column(name="tipo_variable")
	@Getter @Setter private String tipoVariable;
	
	@Getter @Setter private String estado;
	
	//bi-directional many-to-one association to EnunciadoCampo
	@OneToMany(mappedBy="tipoVariable")
	@JsonIgnore
	@Getter @Setter private List<EnunciadoCampo> enunciadoCampo;

}
