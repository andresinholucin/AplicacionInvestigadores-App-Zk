package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;

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
 * The persistent class for the usuario_privilegio database table.
 * 
 */
@Entity
@Table(name="usuario_privilegio")
@NamedQuery(name="UsuarioPrivilegio.findAll", query="SELECT u FROM UsuarioPrivilegio u")
@NoArgsConstructor
public class UsuarioPrivilegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter  private Long id;

	@Getter @Setter  private String estado;

	//bi-directional many-to-one association to Privilegio
	@ManyToOne
	@JoinColumn(name="id_privilegio")
	@Getter @Setter  private Privilegio privilegio;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@Getter @Setter  private Usuario usuario;

	

}