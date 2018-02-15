package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Vlayout;
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
	Combobox combobox;
	Comboitem comboitem;
	Timebox timebox;
	Datebox datebox;
	Button boton;
	Label label;
	Vlayout vlayout;
	
	public PreviaRespuestaControl() {
		previarespuestas= new ArrayList<>();
		previarespuestas= consulta();
		campo= (Campo) Executions.getCurrent().getArg().get("Campo");
		
		//crearcomponente();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		// Permite enlazar los componentes que se asocian con la anotacion @Wire
		Selectors.wireComponents(view, this, false);
		
		crearContexto();
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
	
	public void crearContexto(){
		
		vlayout = new Vlayout();
		vlayout.setParent(formulariocampo);
		
		label= new  Label();
		label.setValue(campo.getEnunciadoCampo().getEnunciado());
		label.setParent(vlayout);
		

		crearcomponente();

		
		label= new Label();
		label.setValue(campo.getDetalle());
		label.setParent(vlayout);
	}
	
	
	
	@Command
	public void clickRespuestasPrevias(){
		try {
			vlayout.detach();
			crearContexto();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
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
		if(campo.getComponente().getIdComponente()!=null){
			
			Integer componente= (int)(long)campo.getComponente().getIdComponente();
			//System.out.println(componente);
			switch (componente){
			case 1:
				//crear caja de texto
				creaCajaTexto();
				break;
			case 2:
				creaOpcionMultiple();
				break;
			case 3:
				creaCasillaVerificacion();
				break;
			case 4:
				creaListaDesplegable();
				break;
			case 5:
				creaEscalaLineal();
				break;
			case 6:
				creaFecha();
				break;
			case 7:
				creaHora();
				break;
				
			}
		}
		
	}
	
	/*
	 * Crear caja de texto
	 */
	public void creaCajaTexto(){
		if(respuestasseleccionada.size()==0){
			textbox = new Textbox();
			textbox.setParent(vlayout);
		}else{
			 
		}
		
	}
	
	/*
	 * Crear Opcion multiple Radio Group y Radio
	 */
	public void creaOpcionMultiple(){
		radiogroup = new Radiogroup();
		radiogroup.setId("rgrupo");
		radiogroup.setOrient("vertical");
		radiogroup.setParent(vlayout);
		
		if(respuestasseleccionada==null || respuestasseleccionada.size()==0){
			for (int i = 0; i < 5; i++) {
				radio= new Radio();
				radio.setLabel("item "+i);
				radio.setParent(radiogroup);
			}	
		}else{
			for (PreviaRespuesta p : respuestasseleccionada) {
				System.out.println(p.getPreviaRespuesta());
				radio= new Radio();
				radio.setLabel(p.getPreviaRespuesta());
				radio.setParent(radiogroup);
				}
		}
		
	}
	
	/*
	 * Crear Casilla de Verificacion
	 */
	public void creaCasillaVerificacion(){
		if(respuestasseleccionada==null || respuestasseleccionada.size()==0){
		for (int i = 0; i < 5; i++) {
			checkbox = new Checkbox();
			checkbox.setLabel("check "+ i);
			checkbox.setParent(vlayout);
		}
		}else{
			for (PreviaRespuesta p : respuestasseleccionada) {
				checkbox = new Checkbox();
				checkbox.setLabel(p.getPreviaRespuesta());
				checkbox.setParent(vlayout);
			}
		}
	}
	
	/*
	 * Crear caja de texto
	 */
	public void creaListaDesplegable(){
		combobox = new Combobox();
		combobox.setParent(vlayout);
		if(respuestasseleccionada==null || respuestasseleccionada.size()==0){
			for (int i = 0; i < 5; i++) {
				comboitem= new Comboitem();
				comboitem.setLabel("comboitem "+i);
				comboitem.setParent(combobox);
			}	
		}else{
			for (PreviaRespuesta p : respuestasseleccionada) {
				comboitem= new Comboitem();
				comboitem.setLabel(p.getPreviaRespuesta());
				comboitem.setParent(combobox);
			}
		}
		
	}
	
	/*
	 * Crear caja de texto
	 */
	public void creaEscalaLineal(){
		textbox = new Textbox();
		textbox.setParent(vlayout);
	}
	
	/*
	 * Crear caja de texto
	 */
	public void creaFecha(){
		textbox = new Textbox();
		textbox.setParent(vlayout);
	}
	
	/*
	 * Crear caja de texto
	 */
	public void creaHora(){
		textbox = new Textbox();
		textbox.setParent(vlayout);
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
