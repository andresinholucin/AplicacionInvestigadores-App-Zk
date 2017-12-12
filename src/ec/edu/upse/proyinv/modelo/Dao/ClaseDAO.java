package ec.edu.upse.proyinv.modelo.Dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ClaseDAO {
	private static final String UNIDAD_PERSISTENCIA = "AplicacionInvestigadores-App-Zk";
	//static implica que solo va a existir unno solo
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
	
	//Almacenar el entityManager creado
	private EntityManager em;
	
	//Crea el entityManager o devuelve el que esta creado
	public EntityManager getEntityManager(){
		if (em == null){
			em = emf.createEntityManager();
		}
		return em;
	} 
	
	/**
	 * Obtiene una conexion JDBC.
	 * @return
	 */
	public Connection abreConexion() {
		EntityManager entityManager; 
		entityManager = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA).createEntityManager();
	    Connection connection = null;
	    entityManager.getTransaction().begin();
	    connection = entityManager.unwrap(Connection.class);
	    return connection;
	  }

	/**
	 * Cierra una conexion JDBC.
	 * @param cn
	 */
	public void cierraConexion(Connection cn) {
		 try {
			cn.close();
			cn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
