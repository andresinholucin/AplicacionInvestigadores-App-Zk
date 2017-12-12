package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.Date;

import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.edu.upse.proyinv.modelo.Proyecto;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import lombok.Getter;
import lombok.Setter;

public class ProyectoNuevoControl {
	@Wire
	private Window winProyectoNuevo;
	
	@Getter @Setter private String nombreProyecto;
	@Getter @Setter private String descripcion;
	
	@Getter @Setter private Proyecto proyecto;
	Conexion con=new Conexion();
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
	}
	
	@Command
	public Boolean grabar(){
		if(nombreProyecto==null){
			Messagebox.show("Ingrese el Nombre del Proyecto!");
			return false;
		}
		if(descripcion==null){
			Messagebox.show("Ingrese la descripcion del Proyecto!");
			return false;
		}
		Date fecha = new Date();
		
		Proyecto pro= new Proyecto();
		pro.setEstado("A");
		pro.setDescripcion(descripcion);
		pro.setProyecto(nombreProyecto);
		pro.setFechaCreacion(fecha);
		
		//System.out.println(pro);
		final String urlregistraproyecto=con.urlcompeta("/proyectos","/registraproyecto/");
		RestTemplate restTemplatepersona = new RestTemplate();
		Proyecto resultproyecto = restTemplatepersona.postForObject( urlregistraproyecto, pro, Proyecto.class);
		System.out.println(resultproyecto);
		
		
		Messagebox.show("Sus Proyecto ha sido guardados con exito!");
		winProyectoNuevo.detach();
		return true;
	}
	
	@Command
	public void salir(){
		winProyectoNuevo.detach();
	}
}
