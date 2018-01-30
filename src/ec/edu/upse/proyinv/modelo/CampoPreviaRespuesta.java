package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="campo_previa_respuesta")
@NoArgsConstructor
public class CampoPreviaRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_campo_previarespuesta")
	@Getter @Setter private Long id;
	
	//bi-directional many-to-one association to Campo
	@ManyToOne
	@JoinColumn(name="id_campo")
	@JsonIgnore
	@Getter @Setter private Campo campo;
	
	//bi-directional many-to-one association to Campo
	@ManyToOne
	@JoinColumn(name="id_previa_respuesta")
	@Getter @Setter private PreviaRespuesta previaRespuesta;
	
	
}
