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
 * The persistent class for the parroquia database table.
 * 
 */
@Entity
@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p")
@NoArgsConstructor
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	@Getter @Setter private int idParroquia;

	@Getter @Setter private String estado;

	@Getter @Setter private String parroquia;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	@JsonIgnore
	@Getter @Setter private Canton canton;

	//bi-directional many-to-one association to Persona
	@OneToMany(mappedBy="parroquia")
	@JsonIgnore
	@Getter @Setter private List<Persona> personas;

	//bi-directional many-to-one association to Universidad
	@OneToMany(mappedBy="parroquia")
	@JsonIgnore
	@Getter @Setter private List<Universidad> universidads;

	
}