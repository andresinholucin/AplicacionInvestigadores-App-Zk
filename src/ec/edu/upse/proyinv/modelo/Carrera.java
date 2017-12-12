package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the carrera database table.
 * 
 */
@Entity
@NamedQuery(name="Carrera.findAll", query="SELECT c FROM Carrera c")
@NoArgsConstructor
public class Carrera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_carrera")
	@Getter @Setter private Long idCarrera;

	@Getter @Setter private String carrera;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Facultad
	@ManyToOne
	@JoinColumn(name="id_facultad")
	@JsonIgnore
	@Getter @Setter private Facultad facultad;

	//bi-directional many-to-one association to CarreraPersona
	@OneToMany(mappedBy="carrera")
	@JsonIgnore
	@Getter @Setter private List<CarreraPersona> carreraPersonas;


}