package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import ec.edu.upse.proyinv.modelo.Campo;
import ec.edu.upse.proyinv.modelo.Componente;
import ec.edu.upse.proyinv.modelo.EnunciadoCampo;
import ec.edu.upse.proyinv.modelo.auxiliares.Conexion;
import lombok.Getter;
import lombok.Setter;

public class InnterfaceVariableControl {
	
	Conexion con= new Conexion();
	@Getter @Setter private String txtBuscar;

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
					if (ec.getEnunciado().toLowerCase().contains(txtBuscar.toLowerCase())){
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
	@Getter @Setter private Campo nuevoCampo=new Campo();
	@Getter @Setter private Campo campoSeleccionado;

	
	/*
	 * boton agragar variable
	 * este boton solo crea una fila en el registro
	 */


	@Command
	@NotifyChange("camposlist")
	public void addvariable(){
		System.out.println("agregar");
		/*
		Campo c= new Campo();
		Componente co=new Componente();
		EnunciadoCampo ec= new EnunciadoCampo();
		c.setComponente(co);
		c.setEnunciadoCampo(ec);
		campoArrayList.add(c);
		setCamposlist(campoArrayList);*/
		camposlist.add(nuevoCampo);
			

	}
	
	/*
	 * quitar variable
	 */
	@Command
	@NotifyChange("camposlist")
	public void quitarvariable(){
		if(ultimoregistro()>=0){
			camposlist.remove(ultimoregistro());
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
	public void clickEnunciadoCampo(){
	}
	
	@Command
	@NotifyChange("camposlist")
	public void doubleclickEnunciadoCampo(){
		if(validacion()==true){
			nuevoCampo.setEnunciadoCampo(enunciadoCampoSeleccionado);;	
			camposlist.set(ultimoregistro(), nuevoCampo);	
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
	public void clickComponente(){
		System.out.println(componenteSelecionado.getComponente());
	}
	
	@Command
	@NotifyChange("camposlist")
	public void doubleclickComponente(){
		//System.out.println(componenteSelecionado.getComponente());
		nuevoCampo.setComponente(componenteSelecionado);
		camposlist.set(ultimoregistro(), nuevoCampo);	
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
		inde =camposlist.size()-1;
		return inde;
	}
	
	public boolean validacion(){
		System.out.println(camposlist.size());
		if (camposlist.isEmpty() || camposlist==null || camposlist.size()==0){
			System.out.println("agrega un campo");
			return false;
		}else if(camposlist.size()<0){
			camposlist.clear();
			return false;
		}else return true;
	}
	
	@Command
	public void ver(){
		for (Campo campo : camposlist) {
			System.out.println(campo.getComponente().getComponente());
		}
	}
	
}
