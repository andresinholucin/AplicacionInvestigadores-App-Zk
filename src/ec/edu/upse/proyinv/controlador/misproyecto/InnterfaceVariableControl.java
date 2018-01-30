package ec.edu.upse.proyinv.controlador.misproyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.ss.formula.functions.Replace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import ec.edu.upse.proyinv.modelo.Campo;
import ec.edu.upse.proyinv.modelo.Componente;
import ec.edu.upse.proyinv.modelo.EnunciadoCampo;
import ec.edu.upse.proyinv.modelo.Interfaz;
import ec.edu.upse.proyinv.modelo.Proyecto;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import lombok.Getter;
import lombok.Setter;

public class InnterfaceVariableControl {
	
	Conexion con= new Conexion();
	@Getter @Setter private String txtBuscar;
	@Getter @Setter private String txtlistarespuesta;
	
	@Getter @Setter private Interfaz interfaz=new Interfaz();
	
	@Wire
	private Window winInterfaceVariable;
	
	@Wire Listcell respuestas;
	
	@Wire
	private Div divrespuesta;
	
	
	Button boton;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		// Permite enlazar los componentes que se asocian con la anotacion @Wire
		Selectors.wireComponents(view, this, false);
	}
	
	/*
	 * 
	 * //para el tratamiento de la lista de los enunciados
	 * 
	 * 
	 */
	
	@Setter List<EnunciadoCampo> enunciadoCamopos;
	ArrayList<EnunciadoCampo> enunciadoCampoarraylist = new ArrayList<EnunciadoCampo>();
	@Getter @Setter private EnunciadoCampo enunciadoCampoSeleccionado;
	
	/*
	 * invocar lista de variables comunes (enunciados) al web service y llenar en el list box
	 */
	public List<EnunciadoCampo> getenunciadoCamopos(){
		try {
			
		if(txtBuscar==null || "".equals(txtBuscar)){
			
			String url=con.urlcompeta("proyectos/","enunciados/");
			System.out.println(url);
			RestTemplate restTemplateenunciado = new RestTemplate();
			ResponseEntity<EnunciadoCampo[]> response= 
				restTemplateenunciado.getForEntity(url, EnunciadoCampo[].class);
				Arrays.asList(response.getBody())
		        .forEach(proyecto -> enunciadoCampoarraylist.add(proyecto));		
			System.out.println(enunciadoCampoarraylist);
			return enunciadoCampoarraylist;
			
		}else{
			List<EnunciadoCampo> result =new ArrayList<EnunciadoCampo>();
				for(EnunciadoCampo ec: enunciadoCampoarraylist  ){
					if (ec.getEnunciado().toLowerCase().contains(txtBuscar.toLowerCase())
						|| ec.getTipoVariable().getTipoVariable().toLowerCase().contains(txtBuscar.toLowerCase())){
							result.add(ec);
						}
				}
			
			return result;
		}
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	/*
	 * boton buscar enunciado
	 */
	@Command
	@NotifyChange("enunciadoCamopos")
	public void buscar(){
		System.out.println("busca");
	}
	
	/*
	 * 
	 * //para el tratamiento del listbox de los campos
	 * 
	 */
	
	@Getter @Setter private List<Campo> camposlist=new ArrayList<>();
	
	ArrayList<Campo> campoArrayList= new ArrayList<Campo>();
	@Getter @Setter private Campo nuevoCampo;
	@Getter @Setter private Campo campoSeleccionado;

	@Getter @Setter private Integer filaActiva;
	
	/*
	 * boton agragar variable
	 * este boton solo crea una fila en el registro
	 */
	@Command
	@NotifyChange("camposlist")
	public void addvariable(){
		//System.out.println("agregar");
		nuevoCampo = new Campo();
		nuevoCampo.setNumeroCampo(ultimoregistro()+1);
		camposlist.add(nuevoCampo);
		campoSeleccionado=null;

	}
	
	/*
	 * quitar variable
	 */
	@Command
	@NotifyChange("camposlist")
	public void quitarvariable(){
		if(ultimoregistro()>0){
			camposlist.remove(ultimoregistro()-1);
		}
		
	}
	
	/*
	 * mover un elemento de la lista enunciadoCampo o componente a la lista de campo
	 */
	@Command
	@NotifyChange("camposlist")
	public void mover(@BindingParam("dComponent") Component dComponent){
		nuevoCampo.setEnunciadoCampo(enunciadoCampoSeleccionado);;	
		camposlist.set(ultimoregistro(), nuevoCampo);	
	}
	
	@Command
	public void clickEnunciadoCampo(){}
	
	@Command
	@NotifyChange("camposlist")
	public void doubleclickEnunciadoCampo(){
		if(campoSeleccionado==null){
			if(validacion()==true){
				nuevoCampo.setEnunciadoCampo(enunciadoCampoSeleccionado);;	
				camposlist.set(ultimoregistro()-1, nuevoCampo);	
			}
		}else{
			if(validacion()==true){
				campoSeleccionado.setEnunciadoCampo(enunciadoCampoSeleccionado);
				
			}
		}
		
		
	}
	
	//para el tratamiento de la lista de componentes
	
	@Getter @Setter private Componente componenteSelecionado;
	
	/*
	 * invocar lista de componentes al web service y llenar
	 */
	public List<Componente> getComponentes(){
		String url=con.urlcompeta("proyectos/","componentes/");
		System.out.println(url);
		try {
			ArrayList<Componente> componentearraylist = new ArrayList<Componente>();
			RestTemplate restTemplatecomponente = new RestTemplate();
			
			ResponseEntity<Componente[]> response= 
					restTemplatecomponente.getForEntity(url, Componente[].class);
			Arrays.asList(response.getBody())
	        .forEach(proyecto -> componentearraylist.add(proyecto));		
			System.out.println(componentearraylist);
			return componentearraylist;
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	@Command
	public void clickComponente(){}
	
	@Command
	@NotifyChange("camposlist")
	public void doubleclickComponente(){
		//System.out.println(componenteSelecionado.getComponente());
		if(campoSeleccionado==null){
			if(validacion()==true){
			nuevoCampo.setComponente(componenteSelecionado);
			camposlist.set(ultimoregistro()-1, nuevoCampo);}	
		}else{
			if(validacion()==true){
				campoSeleccionado.setComponente(componenteSelecionado);
			}
		}
				
	}
	
	/*
	 *salir y regresar pantalla anterior 
	 */
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}

	public int ultimoregistro(){
		int inde;
		if(camposlist==null){
			return 0;
		}else{
			inde =camposlist.size();
		}
		return inde;
	}
	
	public boolean validacion(){
		//System.out.println(camposlist.size());
		if (camposlist.isEmpty() || camposlist==null || camposlist.size()==0){
			System.out.println("agrega un campo");
			Clients.showNotification("Agrega y Edita un Campo");
			return false;
		}else if(camposlist.size()<0){
			camposlist.clear();
			return false;
		}else return true;
	}
	
	@Command
	public void ver() throws IOException{
		for (Campo campo : camposlist) {
			System.out.println(campo.getEnunciadoCampo().getEnunciado());
		}
		
		String url="/vistas/misproyectos/nuevoproyecto/verformulario.zul";
		interfaz.setCampos(camposlist);
		
		
		
		/*
		ObjectMapper mapper = new ObjectMapper();
		  
		String json = mapper.writerWithDefaultPrettyPrinter()
		                    .writeValueAsString(interfaz);
		
		final Gson gson= new Gson();
		String json=gson.toJson(interfaz);
		System.out.println(json);
	
		String urljson=url+"?myId="+json;
		urljson=urljson.replace("\"", "$");
		urljson=urljson.replace(" ", "&");
		Executions.getCurrent().sendRedirect(urljson,"_blank");
		*/
		//Executions.getCurrent().forward("/vistas/misproyectos/nuevoproyecto/new_file.zul");
	}
	
	@Command
	public void clickllamarPosiblesRespuestas(){
	
		
	}
	
	@Command
	public void dobleclickllamarPosiblesRespuestas(){
		
		if(campoSeleccionado==null){
			Clients.showNotification("Selecciona una Fila");
			
		}else if(campoSeleccionado.getComponente().getComponente()==""){
			Clients.showNotification("Agrege un Componente");
			
		}else if(campoSeleccionado.getComponente().getIdComponente()==1 ||
				campoSeleccionado.getComponente().getIdComponente()==5 ||
				campoSeleccionado.getComponente().getIdComponente()==6 ||
				campoSeleccionado.getComponente().getIdComponente()==7){
			Clients.showNotification("Componente No necesita Respuesta Previa");
		}
		
		else{
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Campo", campoSeleccionado);
			System.out.println(campoSeleccionado.toString());
			Window ventanaCargar = (Window) Executions.createComponents("/vistas/misproyectos/nuevoproyecto/previasrespuestas.zul", null, params);
			ventanaCargar.doModal();
		}
			
	
		
	}
	
	@Command
	public void selecciona(){
		System.out.println(campoSeleccionado.getDetalle());
	}
}
