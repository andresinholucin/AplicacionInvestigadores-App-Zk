package ec.edu.upse.proyinv.modelo.Dao;

import java.util.List;

import javax.persistence.Query;

import ec.edu.upse.proyinv.modelo.Proyecto;
import ec.edu.upse.proyinv.modelo.auxiliares.ObtenerCampos;

public class ProyectoDAO extends ClaseDAO{
	
	public Proyecto getProyectoPorId(int id){
		Proyecto proyecto;
		proyecto=(Proyecto)getEntityManager().find(Proyecto.class, id);
		
		return proyecto;
	}
	
	/**
	 * Busca Proyectos en base a un patron de busqueda.
	 * @param value
	 * @return
	 */

	public List<Proyecto> getProyectos(String value) {
		List<Proyecto> resultado;
		String patron = value;

		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}

		// Crea la consulta.
		Query query = getEntityManager().createNamedQuery("Proyecto.buscarPorPatron");

		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		// Recupera los resultados.
		resultado = (List<Proyecto>) query.getResultList();
		System.out.println(resultado);
		return resultado;
	}

	public List<ObtenerCampos> getobtenercampos(int valor){
		List<ObtenerCampos> resultado;
		int patron = valor;
		/*
		StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery("buscaCampos");
		//query.registerStoredProcedureParameter("id_interfaz", Integer.class, ParameterMode.IN);
		//query.registerStoredProcedureParameter("retorno", ObtenerCampos.class, ParameterMode.OUT);
		query.setParameter("id_interfaz", 3);
		query.execute();
		
		List<ObtenerCampos> obte =(List<ObtenerCampos>) query.getOutputParameterValue("retorno");
		
		
		//resultado= (List<ObtenerCampos>) query.getResultList();
		return obte;  
		*/

		Query query = getEntityManager().createNativeQuery("{call buscaCampos(?)}",
                                   ObtenerCampos.class)           
                                   .setParameter(1, 3);

		List<ObtenerCampos> result = query.getResultList();
		return result;
	}
	
}
