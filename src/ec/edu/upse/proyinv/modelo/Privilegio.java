package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the privilegio database table.
 * 
 */
@Entity
@NamedQuery(name="Privilegio.findAll", query="SELECT p FROM Privilegio p")
@NoArgsConstructor
public class Privilegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_privilegio")
	@Getter @Setter private Long idPrivilegio;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to OpcionPrivilegio
	@OneToMany(mappedBy="privilegio")
	@JsonIgnore
	@Getter @Setter private List<OpcionPrivilegio> opcionPrivilegios;

	//bi-directional many-to-one association to UsuarioPrivilegio
	@OneToMany(mappedBy="privilegio")
	@JsonIgnore
	@Getter @Setter private List<UsuarioPrivilegio> usuarioPrivilegios;

	

}