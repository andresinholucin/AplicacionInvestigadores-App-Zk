package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the carrera_persona database table.
 * 
 */
@Entity
@Table(name="carrera_persona")
@NamedQuery(name="CarreraPersona.findAll", query="SELECT c FROM CarreraPersona c")
@NoArgsConstructor
public class CarreraPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_carrera_persona")
	@Getter @Setter private Long idCarreraPersona;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Carrera
	@ManyToOne
	@JoinColumn(name="id_carrera")
	@Getter @Setter private Carrera carrera;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	@Getter @Setter private Persona persona;

	@Override
	public String toString() {
		return "CarreraPersona [idCarreraPersona=" + idCarreraPersona + ", estado=" + estado + ", carrera=" + carrera
				+ ", persona=" + persona + "]";
	}

	
}