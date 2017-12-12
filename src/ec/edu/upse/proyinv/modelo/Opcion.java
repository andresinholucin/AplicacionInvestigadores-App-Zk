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
 * The persistent class for the opcion database table.
 * 
 */
@Entity
@NamedQuery(name="Opcion.findAll", query="SELECT o FROM Opcion o")
@NoArgsConstructor
public class Opcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_opcion")
	@Getter @Setter private Long idOpcion;

	@Getter @Setter private String estado;

	@Getter @Setter private String imagen;

	@Getter @Setter private String titulo;

	@Getter @Setter private String url;

	//bi-directional many-to-one association to Opcion
	@ManyToOne
	@JoinColumn(name="id_opcion_padre")
	@Getter @Setter private Opcion opcion;

	//bi-directional many-to-one association to Opcion
	@OneToMany(mappedBy="opcion")
	@Getter @Setter private List<Opcion> opcions;

	//bi-directional many-to-one association to OpcionPrivilegio
	@OneToMany(mappedBy="opcion")
	@Getter @Setter private List<OpcionPrivilegio> opcionPrivilegios;

	

}