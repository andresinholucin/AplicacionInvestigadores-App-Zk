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
 * The persistent class for the interfaz database table.
 * 
 */
@Entity
@NamedQuery(name="Interfaz.findAll", query="SELECT i FROM Interfaz i")
@NoArgsConstructor
public class Interfaz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_interfaz")
	@Getter @Setter private int idInterfaz;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	@Getter @Setter private String nombreinterfaz;

	//bi-directional many-to-one association to Campo
	@OneToMany(mappedBy="interfaz")
	//@JsonIgnore
	@Getter @Setter private List<Campo> campos;

	//bi-directional many-to-one association to Interfaz
	@ManyToOne
	@JoinColumn(name="id_interfaz_padre")
	@JsonIgnore
	@Getter @Setter private Interfaz interfaz;

	//bi-directional many-to-one association to Interfaz
	@OneToMany(mappedBy="interfaz")
	@JsonIgnore
	@Getter @Setter private List<Interfaz> interfazs;

	//bi-directional many-to-one association to InterfazProyecto
	@OneToMany(mappedBy="interfaz")
	@JsonIgnore
	@Getter @Setter private List<InterfazProyecto> interfazProyectos;

	

}