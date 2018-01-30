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
@Table(name="previa_respuesta")
@NoArgsConstructor
public class PreviaRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_previa_respuesta")
	@Getter @Setter private Long idPreviaRespuesta;
	
	@Column(name="previa_respuesta")
	@Getter @Setter private String previaRespuesta;
	
	@Getter @Setter private String estado;
	
	//bi-directional many-to-one association to CampoPreviaRespuesta
	@OneToMany(mappedBy="previaRespuesta")
	@JsonIgnore
	@Getter @Setter private List<CampoPreviaRespuesta> campoPreviaRespuestas;
}
