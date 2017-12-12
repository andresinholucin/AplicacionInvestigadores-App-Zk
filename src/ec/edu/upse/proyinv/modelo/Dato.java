package ec.edu.upse.proyinv.modelo;

import java.io.Serializable;

import javax.persistence.Column;
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
 * The persistent class for the datos database table.
 * 
 */
@Entity
@Table(name="datos")
@NamedQuery(name="Dato.findAll", query="SELECT d FROM Dato d")
@NoArgsConstructor
public class Dato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_dato")
	@Getter @Setter private Long idDato;

	@Getter @Setter private String dato;

	@Getter @Setter private String estado;

	@Getter @Setter private int numeroRegistro;

	//bi-directional many-to-one association to Campo
	@ManyToOne
	@JoinColumn(name="id_campo")
	@Getter @Setter private Campo campo;

	
}