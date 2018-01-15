package ec.edu.upse.proyinv.modelo.auxiliares;

import lombok.Getter;

public class Conexion {
	//parametros para conectar al web service
	@Getter private String puerto="8081";
	@Getter private String direccion="localhost";
	@Getter private String aplicacion="/AplicacionInvestigadores-WS/api/";
	
	
	
	public Conexion(){}
	
	public String urlcompeta(String ws,String metodo){
		String url="http://"+direccion+":"+puerto+aplicacion+ws+metodo;
		return url;
	}
}
