package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;



import ec.edu.upse.proyinv.modelo.Interfaz;
import ec.edu.upse.proyinv.modelo.InterfazProyecto;
import ec.edu.upse.proyinv.modelo.Proyecto;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import ec.edu.upse.proyinv.modelo.auxiliares.ObtenerCampos;
import lombok.Getter;
import lombok.Setter;

public class MisproyectosControl {
	
	@Getter @Setter public String textoBuscar;	
	
	@Getter @Setter private Proyecto proyectosSeleccionado;
	@Getter @Setter private InterfazProyecto interfazproyectoSeleccionado;
	@Getter @Setter private Interfaz interfazSeleccionado;
	
	Conexion con= new Conexion();
	
	private List<ObtenerCampos> camposbuscar;
	
	public List<Proyecto> getProyectos(){
		String url=con.urlcompeta("/proyectos/","");
		try {
			ArrayList<Proyecto> proyectoarraylist = new ArrayList<Proyecto>();
			System.out.println("0");
			RestTemplate restTemplateproyectos = new RestTemplate();
			System.out.println("1");
			ResponseEntity<Proyecto[]> response= 
					restTemplateproyectos.getForEntity(url, Proyecto[].class);
			Arrays.asList(response.getBody())
	        .forEach(proyecto -> proyectoarraylist.add(proyecto));		
			System.out.println(proyectoarraylist);
			return proyectoarraylist;
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	@Command
	@NotifyChange({"interfazes"})
	public void actualizaCampos(){
	}
	
	
	public List<Interfaz> getInterfazes(){
		if(proyectosSeleccionado==null){
			return null;
		}else{
			ArrayList<Interfaz> interfazArrayList=new ArrayList<Interfaz>();
			
			for(int i=0;i<proyectosSeleccionado.getInterfazProyectos().size();i++){
				//System.out.println(proyectosSeleccionado.getInterfazProyectos().get(i).getInterfaz().getNombreinterfaz());
				interfazArrayList.add(proyectosSeleccionado.getInterfazProyectos().get(i).getInterfaz());
			
			}
			
			return interfazArrayList;
		}
	}
	
	

	
	@Command
	public void buscar(){
		System.out.println("1");	
	}
	

	
	@Command
	@NotifyChange({"camposbuscar"})
	public void buscar2(){
		System.out.println("a");
		if(camposbuscar!=null){
			camposbuscar=null;
		}
		
	}

	@Command 
	public void limpiaCabezeras(){
		
	}

		

	public List<ObtenerCampos> getCamposbuscar() {
		return camposbuscar;
	}

	public void setCamposbuscar(List<ObtenerCampos> camposbuscar) {
		this.camposbuscar = camposbuscar;
	}
	
	/**
	 * Crea una persona
	 */
	@Command
	public void nuevo(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Persona", new Proyecto());
		Window ventanaCargar = (Window) Executions.createComponents("/vistas/misproyectos/proyectoNuevo.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@Command
	public void configurar(){
		if(proyectosSeleccionado==null){
			Clients.showNotification("Debe seleccionar un Proyecto.");
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Proyecto", proyectosSeleccionado);
		Window ventanaCargar = (Window) Executions.createComponents("/vistas/misproyectos/proyectoConfigurar.zul", null, params);
		ventanaCargar.doModal();
	}

}
