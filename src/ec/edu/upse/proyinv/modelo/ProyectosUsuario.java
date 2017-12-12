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
 * The persistent class for the proyectos_usuarios database table.
 * 
 */
@Entity
@Table(name="proyectos_usuarios")
@NamedQuery(name="ProyectosUsuario.findAll", query="SELECT p FROM ProyectosUsuario p")
@NoArgsConstructor
public class ProyectosUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proyecto_usuariosprivilegio")
	@Getter @Setter private int idProyectoUsuariosprivilegio;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="id_proyecto")
	@Getter @Setter private Proyecto proyecto;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@Getter @Setter private Usuario usuario;

	
}