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
 * The persistent class for the provincia database table.
 * 
 */
@Entity
@NamedQuery(name="Provincia.findAll", query="SELECT p FROM Provincia p")
@NoArgsConstructor
public class Provincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_provincia")
	@Getter @Setter private int idProvincia;

	@Getter @Setter private String estado;

	@Getter @Setter private String provincia;

	//bi-directional many-to-one association to Canton
	@OneToMany(mappedBy="provincia")
	@Getter @Setter private List<Canton> cantons;

	//bi-directional many-to-one association to Pai
	@ManyToOne
	@JoinColumn(name="id_pais")
	@JsonIgnore
	@Getter @Setter private Pai pai;

	

}