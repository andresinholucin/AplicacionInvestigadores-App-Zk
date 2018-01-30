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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@NoArgsConstructor
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	@Getter @Setter private Long idUsuario;

	@Getter @Setter private String clave;

	@Getter @Setter private String estado;

	@Getter @Setter private String usuario;

	//bi-directional many-to-one association to ProyectosUsuario
	@OneToMany(mappedBy="usuario")
	@Getter @Setter private List<ProyectosUsuario> proyectosUsuarios;

	//bi-directional many-to-one association to UsuarioPrivilegio
	@OneToMany(mappedBy="usuario")
	@Getter @Setter private List<UsuarioPrivilegio> usuarioPrivilegios;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	@Getter @Setter private Persona persona;

	

}