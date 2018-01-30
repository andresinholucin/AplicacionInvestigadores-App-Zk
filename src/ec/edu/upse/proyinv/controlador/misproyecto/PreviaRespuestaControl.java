package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import ec.edu.upse.proyinv.modelo.Campo;
import ec.edu.upse.proyinv.modelo.Persona;
import ec.edu.upse.proyinv.modelo.PreviaRespuesta;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import lombok.Getter;
import lombok.Setter;

public class PreviaRespuestaControl {

	Conexion con= new Conexion();
	
	@Getter @Setter private String txtAccionPR;
	
	//Lista de Respuestas que viene del web service
	ArrayList<PreviaRespuesta> previarespuestaarraylist = new ArrayList<PreviaRespuesta>();
	
	//relacion instancia con la listbox de la lista
	@Getter @Setter private List<PreviaRespuesta> previarespuestas;
	
	//relacion instancia con los elementos selecionado con el check
	@Getter @Setter private List<PreviaRespuesta> respuestasseleccionada;
	
	@Getter @Setter private Campo campo;
	
	@Wire private Window formulariocampo;
	
	Textbox textbox;
	Radiogroup radiogroup;
	Radio radio;
	Checkbox checkbox;
	Listbox listbox;
	Timebox timebox;
	Datebox datebox;
	
	public PreviaRespuestaControl() {
		previarespuestas= new ArrayList<>();
		previarespuestas= consulta();
		campo= (Campo) Executions.getCurrent().getArg().get("Campo");
		System.out.println(campo.getDetalle());
		crearcomponente();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		// Permite enlazar los componentes que se asocian con la anotacion @Wire
		Selectors.wireComponents(view, this, false);
	
	}

	/*
	 * invocar lista de Respuestas previas al web service y llenar
	 */

	public List<PreviaRespuesta> consulta(){
		String url=con.urlcompeta("proyectos/","previarespuesta/");
		//System.out.println(url);
		previarespuestaarraylist.clear();
		try {
			RestTemplate restTemplatecomponente = new RestTemplate();
			
			ResponseEntity<PreviaRespuesta[]> response= 
					restTemplatecomponente.getForEntity(url, PreviaRespuesta[].class);
			Arrays.asList(response.getBody())
	        .forEach(proyecto -> previarespuestaarraylist.add(proyecto));		
			System.out.println(previarespuestaarraylist);
			return previarespuestaarraylist;
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	public List<PreviaRespuesta> busqueda(){
		try {
			ArrayList<PreviaRespuesta> busquedarespuesta= new ArrayList<PreviaRespuesta>();
			for (PreviaRespuesta pv : previarespuestaarraylist) {
				if (pv.getPreviaRespuesta().toLowerCase().contains(txtAccionPR.toLowerCase())){
					busquedarespuesta.add(pv);
					System.out.println(pv.getPreviaRespuesta());
				}
			}
			return busquedarespuesta;
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	
	@Command
	public void clickRespuestasPrevias(){}
	
	@Command
	public void dobleclickRespuestasPrevias(){}
	
	/*
	 * boton buscar enunciado
	 */
	@Command
	@NotifyChange("previarespuestas")
	public void buscar(@BindingParam("botonrespuesta") Button botonrespuesta){
		if(botonrespuesta.getLabel().equals("Buscar!")){
			previarespuestas= busqueda();
			if(previarespuestas.isEmpty()){
				System.out.println("esta vacio");
				botonrespuesta.setLabel("Add");
				botonrespuesta.setIconSclass("z-icon-plus");
			}else{
				botonrespuesta.setLabel("Buscar!");
				botonrespuesta.setIconSclass("z-icon-search");
			}
		}else if(botonrespuesta.getLabel().equals("Add")){
			System.out.println("llena un registro");
			if(registrar()){
				Clients.showNotification("listo");
				previarespuestas= consulta();
				botonrespuesta.setLabel("Buscar!");
				botonrespuesta.setIconSclass("z-icon-search");
			}else{
				Clients.showNotification("ha ocurrido un error!");
			}
		}
		
	}
	
	//registrar
	public Boolean registrar(){
		try {
			PreviaRespuesta pr= new PreviaRespuesta();
			pr.setEstado("A");
			pr.setPreviaRespuesta(txtAccionPR);
			
			final String urlpr=con.urlcompeta("/proyectos","/registrarespuesta/");
			RestTemplate restTemplatepr = new RestTemplate();
			PreviaRespuesta resultpr = restTemplatepr.postForObject( urlpr, pr, PreviaRespuesta.class);
			System.out.println(resultpr);
			
			return true;
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}
	
	/*
	 * crear componente
	 */
	@NotifyChange("formulariocampo")
	public void crearcomponente(){
		Integer componente= (int)(long)campo.getComponente().getIdComponente();
		System.out.println(componente);
		switch (componente){
		case 1:
			//crear caja de texto
			creaCajaTexto();
			break;
		case 2:
			creaCajaTexto();
			break;
		
		}
	}
	
	/*
	 * Crear caja de texto
	 */
	@NotifyChange("formulariocampo")
	public void creaCajaTexto(){
		textbox = new Textbox();
		textbox.setParent(formulariocampo);
	}
	
	
	/*
	 *salir y regresar pantalla anterior 
	 */
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}
	
	@Command
	public void clickmostrarseleccionados(){
		for (PreviaRespuesta p : respuestasseleccionada) {
		System.out.println(p.getPreviaRespuesta());
		}
	}
}
