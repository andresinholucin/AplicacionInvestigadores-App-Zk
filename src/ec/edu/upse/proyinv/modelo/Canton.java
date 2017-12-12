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
 * The persistent class for the canton database table.
 * 
 */
@Entity
@NamedQuery(name="Canton.findAll", query="SELECT c FROM Canton c")
@NoArgsConstructor
public class Canton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_canton")
	@Getter @Setter private int idCanton;

	@Getter @Setter private String canton;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Provincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	@JsonIgnore
	@Getter @Setter private Provincia provincia;

	//bi-directional many-to-one association to Parroquia
	@OneToMany(mappedBy="canton")
	@Getter @Setter private List<Parroquia> parroquias;

	
}