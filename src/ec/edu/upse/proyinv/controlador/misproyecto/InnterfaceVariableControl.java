package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
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
	
	private List<Campo> camposlist;
	
	ArrayList<Campo> campoArrayList= new ArrayList<Campo>();
	@Getter @Setter private Campo nuevoCampo;
	@Getter @Setter private Campo campoSeleccionado;


	public List<Campo> getCamposlist() {
		return camposlist;
	}

	public void setCamposlist(List<Campo> camposlist) {
		this.camposlist = camposlist;
	}

	
	/*
	 * boton agragar variable
	 * este boton solo crea una fila en el registro
	 */


	@Command
	@NotifyChange("camposlist")
	public void addvariable(){
		System.out.println("agregar");
		
		Campo c= new Campo();
		Componente co=new Componente();
		EnunciadoCampo ec= new EnunciadoCampo();
		/*co.setComponente("componente");
		ec.setEnunciado("NOmbre");
		c.setNombreCampo("a");*/
		c.setComponente(co);
		c.setEnunciadoCampo(ec);
		campoArrayList.add(c);
		
		setCamposlist(campoArrayList);
			
		System.out.println(camposlist.size());
		//getCamposlist();
	}
	/*
	 * quitar variable
	 */
	@Command
	@NotifyChange("camposlist")
	public void quitarvariable(){
		System.out.println("quitar");
		int ultimo;
		ultimo =camposlist.size();
		System.out.println(ultimo);
		camposlist.remove(ultimo-1);
	}
	
	
	
	
	
	//para el tratamiento de la lista de componentes
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
	
	
	
	
	
	
	/*
	 *salir y regresar pantalla anterior 
	 */
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}


	
	
	
}
