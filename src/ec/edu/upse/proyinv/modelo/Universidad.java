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
 * The persistent class for the universidad database table.
 * 
 */
@Entity
@NamedQuery(name="Universidad.findAll", query="SELECT u FROM Universidad u")
@NoArgsConstructor
public class Universidad implements Serializable {
	private  static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_universidad")
	@Getter @Setter private  Long idUniversidad;

	@Getter @Setter private  String correo;

	@Getter @Setter private  String direccion;

	@Getter @Setter private  String estado;

	@Column(name="representante_legal")
	@Getter @Setter private  String representanteLegal;

	@Getter @Setter private  String universidad;

	
	//bi-directional many-to-one association to Facultad
	@OneToMany(mappedBy="universidad")
	@Getter @Setter private  List<Facultad> facultads;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	@JsonIgnore
	@Getter @Setter private  Parroquia parroquia;

}