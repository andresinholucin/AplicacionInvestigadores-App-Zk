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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the interfaz_proyecto database table.
 * 
 */
@Entity
@Table(name="interfaz_proyecto")
@NamedQuery(name="InterfazProyecto.findAll", query="SELECT i FROM InterfazProyecto i")
@NoArgsConstructor
public class InterfazProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_interfaz_proyecto")
	@Getter @Setter private int idInterfazProyecto;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to Interfaz
	@ManyToOne
	@JoinColumn(name="id_interfaz")
	@Getter @Setter private Interfaz interfaz;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="id_proyecto")
	@JsonIgnore
	@Getter @Setter private Proyecto proyecto;

	

}