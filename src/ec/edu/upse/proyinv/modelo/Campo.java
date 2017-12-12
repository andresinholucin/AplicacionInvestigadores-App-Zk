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
 * The persistent class for the campo database table.
 * 
 */
@Entity
@NamedQuery(name="Campo.findAll", query="SELECT c FROM Campo c")
@NoArgsConstructor
public class Campo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_campo")
	@Getter @Setter private int idCampo;

	@Getter @Setter private String detalle;

	@Getter @Setter private String estado;

	@Column(name="key_llave")
	@Getter @Setter private String keyLlave;

	@Column(name="nombre_campo")
	@Getter @Setter private String nombreCampo;

	@Column(name="numero_campo")
	@Getter @Setter private int numeroCampo;

	@Column(name="tipo_dato")
	@Getter @Setter private String tipoDato;

	//bi-directional many-to-one association to Interfaz
	@ManyToOne
	@JoinColumn(name="id_interfaz")
	@Getter @Setter private Interfaz interfaz;

	//bi-directional many-to-one association to Dato
	@OneToMany(mappedBy="campo")
	@Getter @Setter private List<Dato> datos;

	

}