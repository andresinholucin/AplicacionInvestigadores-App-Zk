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
 * The persistent class for the facultad database table.
 * 
 */
@Entity
@NamedQuery(name="Facultad.findAll", query="SELECT f FROM Facultad f")
@NoArgsConstructor
public class Facultad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_facultad")
	@Getter @Setter private Long idFacultad;

	@Getter @Setter private String estado;

	@Getter @Setter private String facultad;

	//bi-directional many-to-one association to Carrera
	@OneToMany(mappedBy="facultad")
	@Getter @Setter private List<Carrera> carreras;
	
	//bi-directional many-to-one association to Universidad
	@ManyToOne
	@JoinColumn(name="id_universidad")
	@JsonIgnore
	@Getter @Setter private Universidad universidad;
	 
	
}