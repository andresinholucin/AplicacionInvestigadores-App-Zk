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
import javax.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="pais")
@NamedQuery(name="Pai.findAll", query="SELECT p FROM Pai p")
@NoArgsConstructor
public class Pai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pais")
	@Getter @Setter private Long idPais;

	@Getter @Setter private String estado;

	@Getter @Setter private String pais;

	//bi-directional many-to-one association to Provincia
	@OneToMany(mappedBy="pai")
	@Getter @Setter private List<Provincia> provincias;

}