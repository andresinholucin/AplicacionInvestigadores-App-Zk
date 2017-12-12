package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
@NoArgsConstructor
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona")
	@Getter @Setter private Long idPersona;

	@Getter @Setter private String apellido;

	@Getter @Setter private String cedula;

	@Getter @Setter private String direccion;

	@Getter @Setter private String estado;
	@Getter @Setter private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	@Getter @Setter private Date fechaNacimiento;

	@Getter @Setter private String nacionalidad;

	@Getter @Setter private String nombre;

	@Getter @Setter private String sexo;

	//bi-directional many-to-one association to CarreraPersona
	@OneToMany(mappedBy="persona")
	@Getter @Setter private List<CarreraPersona> carreraPersonas;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	@Getter @Setter private Parroquia parroquia;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="persona")
	@Getter @Setter private List<Usuario> usuarios;

	

}