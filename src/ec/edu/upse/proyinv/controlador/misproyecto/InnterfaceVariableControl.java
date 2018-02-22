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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import ec.edu.upse.proyinv.modelo.Campo;
import ec.edu.upse.proyinv.modelo.CampoPreviaRespuesta;
import ec.edu.upse.proyinv.modelo.Componente;
import ec.edu.upse.proyinv.modelo.EnunciadoCampo;
import ec.edu.upse.proyinv.modelo.Interfaz;
import ec.edu.upse.proyinv.modelo.PreviaRespuesta;
import ec.edu.upse.proyinv.modelo.TipoVariable;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import lombok.Getter;
import lombok.Setter;

public class InnterfaceVariableControl {
	
	Conexion con= new Conexion();

	@Getter @Setter private String txtlistarespuesta;
	
	@Getter @Setter private Interfaz interfaz=new Interfaz();
	
	@Wire
	private Window winInterfaceVariable;
	
	@Wire Listcell respuestas;
	
	@Wire
	private Div divrespuesta;
	
	@Wire private Div addopcion;

	@Wire private Window winVerFormulario;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		// Permite enlazar los componentes que se asocian con la anotacion @Wire
		Selectors.wireComponents(view, this, false);
		
		previaRespuestas=consultaPreviasRespuestas();
		enunciadoCamopos=consultaEnunciadosCampos();
		componentes=consultaComponentes();
		crearFormulario();
		
	}
	
	/*
	 * 
	 * //para el tratamiento de la lista de los enunciados
	 * 
	 */

	
	//Lista de EnunciadoCampo que viene del web service
	ArrayList<EnunciadoCampo> enunciadoCampoarraylist = new ArrayList<EnunciadoCampo>();
	//Lista que se llena e interactua en el Listbox 
	@Getter @Setter List<EnunciadoCampo> enunciadoCamopos;
	//EnunciadoCampo seleccionado
	@Getter @Setter private EnunciadoCampo enunciadoCampoSeleccionado;
	
	//control de la caja de texto
	@Getter @Setter private String txtBuscar;
	
	@Getter @Setter private Listbox listboxenunciado;
	
	//consulta al web service los enunciados campos
	public List<EnunciadoCampo> consultaEnunciadosCampos(){
		try {
			String url=con.urlcompeta("proyectos/","enunciados/");
			RestTemplate restTemplateenunciado = new RestTemplate();
			ResponseEntity<EnunciadoCampo[]> response= 
				restTemplateenunciado.getForEntity(url, EnunciadoCampo[].class);
				Arrays.asList(response.getBody())
		        .forEach(proyecto -> enunciadoCampoarraylist.add(proyecto));		
			System.out.println(enunciadoCampoarraylist);
			return enunciadoCampoarraylist;
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
			return null;
		}
	}
	
	//buscar Enunciados
	public List<EnunciadoCampo> buscarEnunciadoCampo(){
		List<EnunciadoCampo> result =new ArrayList<EnunciadoCampo>();
		for(EnunciadoCampo ec: enunciadoCampoarraylist  ){
			if (ec.getEnunciado().toLowerCase().contains(txtBuscar.toLowerCase())
				|| ec.getTipoVariable().getTipoVariable().toLowerCase().contains(txtBuscar.toLowerCase())){
					result.add(ec);
				}
		}
		return result;
	}
	
	/*
	 * boton  AccionBotonEnunciado
	 */
	@Command
	@NotifyChange("enunciadoCamopos")
	public void AccionBotonEnunciado(@BindingParam("btnEnunciado") Button btnEnunciado){
		//System.out.println("busca");
		if(btnEnunciado.getLabel().equals("Buscar!")){
			enunciadoCamopos=buscarEnunciadoCampo();
			if(enunciadoCamopos.isEmpty()){
				btnEnunciado.setLabel("Agregar!");
				btnEnunciado.setIconSclass("z-icon-plus");
			}else{
				btnEnunciado.setLabel("Buscar!");
				btnEnunciado.setIconSclass("z-icon-search");
			}
		}else if(btnEnunciado.getLabel().equals("Agregar!")){
			
			
			if(addEnunciado()){
				Clients.showNotification("listo");
				btnEnunciado.setLabel("Buscar!");
				btnEnunciado.setIconSclass("z-icon-search");
			}else{
				Clients.showNotification("ha ocurrido un error!");
			}
		}		
	}
	
	/*
	 * agrega un enunciado nuevo
	 */
	public boolean addEnunciado(){
		if(validacion()){
			System.out.println("1");
		}else{
			System.out.println("2");
		}
		
		TipoVariable tv= new TipoVariable();
		try {
			String url=con.urlcompeta("proyectos/","tipovariable/");
			System.out.println(url);
			RestTemplate restTemplateenunciado = new RestTemplate();
			ResponseEntity<TipoVariable> response= 
				restTemplateenunciado.getForEntity(url, TipoVariable.class);
			tv=response.getBody();
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
				
		EnunciadoCampo ec= new EnunciadoCampo();
		ec.setEnunciado(txtBuscar);
		ec.setTipoVariable(tv);
		ec.setEstado("A");
		
		enunciadoCampoarraylist.add(ec);
		
		System.out.println("agregaste Enunciado");
		return true;
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
	
	
	/*
	 * 
	 * //para el tratamiento del listbox de los campos
	 * 
	 */
	
	//instancia con la lista de campos y el listbox
	@Getter @Setter private List<Campo> camposlist=new ArrayList<>();
	//agregar campo cada vez que se agrege una fila 
	@Getter @Setter private Campo nuevoCampo;
	//instancia con el campo seleccionado
	@Getter @Setter private Campo campoSeleccionado;

	
	/*
	 * boton agragar variable
	 * este boton solo crea una fila en el registro
	 */
	@Command
	@NotifyChange("camposlist")
	public void addvariable(){
		//System.out.println("agregar");
		nuevoCampo = new Campo();
		cprArrayList= new ArrayList<>();
		nuevoCampo.setNumeroCampo(ultimoregistro()+1);
		nuevoCampo.setCampoPreviaRespuestas(cprArrayList);
		camposlist.add(nuevoCampo);
		
		
		
		campoSeleccionado=null;
		
		
		preRes= null;
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
	
	public int ultimoregistro(){
		int inde;
		if(camposlist==null){
			return 0;
		}else{
			inde =camposlist.size();
		}
		return inde;
	}

	/*
	 * 
	 * //para el tratamiento de la lista de componentes
	 * 
	 */
	
	//Lista de Componente que viene del web service
	ArrayList<Componente> componentearraylist = new ArrayList<Componente>();
	//Lista que se llena e interactua en el Listbox 
	@Getter @Setter List<Componente> componentes;
	//EnunciadoCampo seleccionado
	@Getter @Setter private Componente componenteSeleccionado;
		
	
	//consulta al web service los Componentes
	public List<Componente> consultaComponentes(){
			try {
				String url=con.urlcompeta("proyectos/","componentes/");
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
	@NotifyChange({"camposlist","addopcion"})
	public void clickComponente(){
	}
	
	@Command
	@NotifyChange({"camposlist","addopcion"})
	public void doubleclickComponente(){
		//System.out.println(componenteSelecionado.getComponente());
		if(campoSeleccionado==null){
			if(validacion()==true){
			nuevoCampo.setComponente(componenteSeleccionado);
			camposlist.set(ultimoregistro()-1, nuevoCampo);
			mostrar();
			}
			
		}else{
			if(validacion()==true){
				campoSeleccionado.setComponente(componenteSeleccionado);
				mostrar();
			}
		}
				
	}
	
	@NotifyChange("addopcion")
	public void mostrar(){
		System.out.println(componenteSeleccionado.getIdComponente());
		if(componenteSeleccionado.getIdComponente()==(2) 
				|| componenteSeleccionado.getIdComponente()==3
				|| componenteSeleccionado.getIdComponente()==4){
			addopcion.setVisible(true);
			System.out.println("visible");
		}else{
			addopcion.setVisible(false);
			System.out.println("invisble");
		}
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
	public void ver() {		
		crearFormulario();	
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
	
	/*
	 * 
	 * para el tratamiento del combo de agregar opcion
	 * 
	 */
	
	//Lista de PreviaRespuestas que viene del web service
	ArrayList<PreviaRespuesta> previarespuestaarraylist = new ArrayList<>();
	//Lista que se llena e interactua en el combo sin boton 
	@Getter @Setter List<PreviaRespuesta> previaRespuestas;
	//respuesta que selecciona al dar enter(Ok)
	@Getter @Setter PreviaRespuesta previaRespuestaSelecionada;
	//Combo para extraer la opcion que no se encuentra oir default
	@Wire private Combobox cbpr;
	

	//arreglo de campospreviasRespuestas que se llena  
	@Getter @Setter List<CampoPreviaRespuesta> cprArrayList;
	
	//Lista que se llena en cada campo mientras se va creando para agregar las respuestas
	ArrayList<PreviaRespuesta> previaRespuestacampo;	
	//Lista que interactua con la lista en las respuestas
	@Getter @Setter List<PreviaRespuesta> preRes;	
	//consulta al web service de las respuestas previas
	public List<PreviaRespuesta> consultaPreviasRespuestas(){
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
	
	//esto se ejecuta al dar Ok selecciona respuesta por default o nuevo
	@Command
	@NotifyChange("camposlist")
	public void agregaRespuesta(){
		try {
			
			if(previaRespuestaSelecionada==null){
				addNuevo();
							
			}else{
				addExistente();
				
			}
			//preRes=previaRespuestacampo;
			//asignarCampoPreviaRespuesta();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//agrega una opcion que existe por default
	public void addExistente(){
		CampoPreviaRespuesta cpr=new CampoPreviaRespuesta();
		cpr.setPreviaRespuesta(previaRespuestaSelecionada);
		cprArrayList.add(cpr);
		nuevoCampo.setCampoPreviaRespuestas(cprArrayList);
		
		/*previaRespuestacampo.add(previaRespuestaSelecionada);
		for (PreviaRespuesta p : previaRespuestacampo) {
			System.out.println(p.getPreviaRespuesta());
		}*/
		
	}
	
	//agrega una opcion nueva
	public void addNuevo(){
		PreviaRespuesta pr= new PreviaRespuesta();
		pr.setPreviaRespuesta(cbpr.getText());
		previaRespuestacampo.add(pr);
		
		for (PreviaRespuesta p : previaRespuestacampo) {
			System.out.println(p.getPreviaRespuesta());
		}
	}
	
	public void asignarCampoPreviaRespuesta(){
		
		for(int i=0;i<previaRespuestacampo.size();i++){
			CampoPreviaRespuesta cpr= new CampoPreviaRespuesta();
			cpr.setPreviaRespuesta(previaRespuestacampo.get(i));
			cprArrayList.add(cpr);
		}
		nuevoCampo.setCampoPreviaRespuestas(cprArrayList);
	}
	
	
	
	
	
	
	/*
	 * 
	 * dibujar el formulario
	 * 
	 */
	Textbox textbox;
	
	Radiogroup radiogroup;
	Radio radio;
	
	Checkbox checkbox;
	
	Listbox listbox;
	Listhead listhead;
	Listheader listheader;
	Listitem listitem;
	Listcell listcell;
	
	Combobox combobox;
	Comboitem comboitem;
	
	Timebox timebox;
	
	Datebox datebox;
	
	Button boton;
	
	Label label;
	
	Vlayout vlayout= new Vlayout();
	Hlayout hlayout;
	
	public void crearFormulario(){
		//int n=camposlist.size();
		vlayout.detach();
		vlayout= new Vlayout();
		vlayout.setParent(winVerFormulario);
		if(camposlist.isEmpty()){
			System.out.println("dibuja por defecto");
			formulariodefault();
			
		}else{
			System.out.println("dibuja la lista");
			formulariolista();
		}
		
	}
	
	public void formulariodefault(){
		winVerFormulario.setTitle("Formulario");

		listbox = new Listbox();
		listbox.setWidth("100%");
		listbox.setParent(vlayout);
		
		listhead= new Listhead();
		listhead.setSizable(true);
		listhead.setParent(listbox);
		
		//cabezera 1 del listbox 
		listheader= new Listheader();
		listheader.setLabel("Componente");
		listheader.setHflex("1");
		listheader.setParent(listhead);
		
		//cabezera 2 del listbox
		listheader= new Listheader();
		listheader.setLabel("Ejemplo");
		listheader.setHflex("2");
		listheader.setParent(listhead);
		
		//inicio del primer item - fila de la lista - caja de texto
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Caja de texto");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		label= new Label();
		label.setValue("Nombre");
		label.setParent(listcell);
		textbox = new Textbox();
		textbox.setParent(listcell);
		//fin del primer item - fila de la lista
		
		//inicio del segundo item - fila de la lista - opcion multiple
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Opcion Multiple");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		radiogroup = new Radiogroup();
		radiogroup.setOrient("vertical");
		radiogroup.setParent(listcell);
		
		label = new Label();
		label.setValue("Sexo: ");
		label.setParent(radiogroup);
		
		radio = new Radio();
		radio.setLabel("Masculino");
		radio.setParent(radiogroup);
		
		radio= new Radio();
		radio.setLabel("Femenino");
		radio.setParent(radiogroup);
		//fin del segundo item - fila de la lista
		
		//inicio del tercer item - fila de la lista - casilla de verificacion
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Casilla de Verificacion");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		label= new Label();
		label.setValue("Titulo: ");
		label.setParent(listcell);
		
		checkbox= new Checkbox();
		checkbox.setLabel("Ingeniero");
		checkbox.setParent(listcell);
		
		checkbox= new Checkbox();
		checkbox.setLabel("Licenciado");
		checkbox.setParent(listcell);
		
		checkbox= new Checkbox();
		checkbox.setLabel("Arquitecto");
		checkbox.setParent(listcell);
		//fin del tercer item - fila de la lista

		//inicio del cuarto item - fila de la lista - lista desplegable
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Lista Desplegable");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		label= new Label();
		label.setValue("Nacionalidad: ");
		label.setParent(listcell);

		combobox = new Combobox();
		combobox.setParent(listcell);
		
		comboitem= new Comboitem(); 
		comboitem.setLabel("Ecuador");
		comboitem.setParent(combobox);
		
		comboitem= new Comboitem(); 
		comboitem.setLabel("Colombia");
		comboitem.setParent(combobox);
		
		comboitem= new Comboitem(); 
		comboitem.setLabel("Peru");
		comboitem.setParent(combobox);
		
		comboitem= new Comboitem(); 
		comboitem.setLabel("Venezuela");
		comboitem.setParent(combobox);
		//fin del cuarto item - fila de la lista
		
		//inicio del quinto item - fila de la lista - escala lineal
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Escala Lineal");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		//fin del quinto item - fila de la lista
		
		//inicio del Sexto item - fila de la lista - fecha
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Fecha");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		datebox = new Datebox();
		datebox.setParent(listcell);
		//fin del Sexto item - fila de la lista
		
		//inicio del septimo item - fila de la lista - hora
		listitem= new Listitem();
		listitem.setParent(listbox);
		
		listcell= new Listcell();
		listcell.setLabel("Hora");
		listcell.setParent(listitem);
		
		listcell= new Listcell();
		listcell.setParent(listitem);
		
		timebox= new Timebox();
		timebox.setFormat("short");
		timebox.setCols(12);
		
		timebox.addEventListener("onCreate", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception{
				System.out.println("creaste time");
			}
		});
	
		timebox.setParent(listcell);
		//fin del septimo item - fila de la lista
	}
	
	public void formulariolista(){
		int i=0;
		for (Campo campo : camposlist) {
			i=i+1;
			hlayout = new Hlayout();
			hlayout.setParent(vlayout);
			
			label= new Label();
			label.setValue(campo.getEnunciadoCampo().getEnunciado());
			label.setParent(hlayout);
			crearcomponente(campo);
			
		}
	}
	
	public void crearcomponente(Campo c){
		Integer componente= (int)(long)c.getComponente().getIdComponente();
		
		switch (componente){
		case 1:
			//crear caja de texto
			creaCajaTexto();
			break;
		case 2:
			creaOpcionMultiple(c);
			break;
		case 3:
			creaCasillaVerificacion(c);
			break;
		case 4:
			creaListaDesplegable(c);
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
	
	/*
	 * Crear caja de texto
	 */
	public void creaCajaTexto(){
			textbox = new Textbox();
			textbox.setParent(hlayout);
	}
	
	/*
	 * Crear Opcion multiple Radio Group y Radio
	 */
	public void creaOpcionMultiple(Campo c){
		radiogroup = new Radiogroup();
		//radiogroup.setId("rgrupo");
		radiogroup.setOrient("vertical");
		radiogroup.setParent(hlayout);

		if(c.getCampoPreviaRespuestas()==null || c.getCampoPreviaRespuestas().size()==0){
			for (int i = 0; i < 5; i++) {
				radio= new Radio();
				radio.setLabel("item "+i);
				radio.setParent(radiogroup);
			}	
		}else{
			
			for (CampoPreviaRespuesta cpr : c.getCampoPreviaRespuestas()) {
				System.out.println(cpr.getPreviaRespuesta());
				radio= new Radio();
				radio.setLabel(cpr.getPreviaRespuesta().getPreviaRespuesta());
				radio.setParent(radiogroup);
				}
		}
		
	}
	
	/*
	 * Crear Casilla de Verificacion
	 */
	public void creaCasillaVerificacion(Campo c){
		Vlayout v= new Vlayout();
		v.setParent(hlayout);
		if(c.getCampoPreviaRespuestas()==null || c.getCampoPreviaRespuestas().size()==0){
			for (int i = 0; i < 5; i++) {
				checkbox = new Checkbox();
				checkbox.setLabel("check "+ i);
				checkbox.setParent(v);
			}
			}else{
				for (CampoPreviaRespuesta cpr : c.getCampoPreviaRespuestas()) {
					checkbox = new Checkbox();
					checkbox.setLabel(cpr.getPreviaRespuesta().getPreviaRespuesta());
					checkbox.setParent(v);
				}
			}
	}
	
	/*
	 * Crear Lista desplegable
	 */
	public void creaListaDesplegable(Campo c){
		combobox = new Combobox();
		combobox.setParent(hlayout);
		if(c.getCampoPreviaRespuestas()==null || c.getCampoPreviaRespuestas().size()==0){
			for (int i = 0; i < 5; i++) {
				comboitem= new Comboitem();
				comboitem.setLabel("comboitem "+i);
				comboitem.setParent(combobox);
			}	
		}else{
			for (CampoPreviaRespuesta cpr : c.getCampoPreviaRespuestas()) {
				comboitem= new Comboitem();
				comboitem.setLabel(cpr.getPreviaRespuesta().getPreviaRespuesta());
				comboitem.setParent(combobox);
			}
		}
		
	}
	
	/*
	 * Crear combos para escala lineal
	 */
	public void creaEscalaLineal(){
		textbox = new Textbox();
		textbox.setParent(hlayout);
	}
	
	/*
	 * Crear fecha
	 */
	public void creaFecha(){
		datebox = new Datebox();
		datebox.setParent(hlayout);
	}
	
	/*
	 * Crear Hora
	 */
	public void creaHora(){
		timebox.addEventListener("onCreate", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception{
				System.out.println("creaste time");
			}
		});
		timebox= new Timebox();
		timebox.setFormat("short");
		timebox.setCols(12);
		timebox.setParent(hlayout);
	}
	
	/*
	 *salir y regresar pantalla anterior 
	 */
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}
}
