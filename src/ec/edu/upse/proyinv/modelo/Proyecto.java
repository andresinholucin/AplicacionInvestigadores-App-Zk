package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the proyectos database table.
 * 
 */
@Entity
@Table(name="proyectos")
@NamedQuery(name="Proyecto.findAll", query="SELECT p FROM Proyecto p")
@NoArgsConstructor
public class Proyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proyecto")
	@Getter @Setter private Long idProyecto;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	@Getter @Setter private Date fechaCreacion;

	@Getter @Setter private String proyecto;

	//bi-directional many-to-one association to InterfazProyecto
	@OneToMany(mappedBy="proyecto")
	@Getter @Setter private List<InterfazProyecto> interfazProyectos;

	//bi-directional many-to-one association to ProyectosUsuario
	@OneToMany(mappedBy="proyecto")
	@JsonIgnore
	@Getter @Setter private List<ProyectosUsuario> proyectosUsuarios;

}