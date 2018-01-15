package ec.edu.upse.proyinv.controlador.misproyecto;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

import ec.edu.upse.proyinv.modelo.Proyecto;

public class InterfaceNuevoControl extends Div implements IdSpace{
	private static final long serialVersionUID = 5183321186606483396L;
	
	public InterfaceNuevoControl(){
		//creacion de la cabezera de interfaces
				Executions.createComponents("/vistas/misproyectos/nuevoproyecto/cabezerainterface.zul", this, null);
				Selectors.wireComponents(this, this, false);
				Selectors.wireEventListeners(this, this);
				//chosenLb.setModel(chosenDataModel = new ListModelList<Person>());
				//chosenDataModel.setMultiple(true);
	}
	
	@Listen("onClick=#addinterface")
	public void irinterfacevariables(){
		System.out.println("asdasd");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Persona", new Proyecto());
		Window ventanaCargar = (Window) Executions.createComponents("/vistas/misproyectos/nuevoproyecto/creainterfacevariables.zul", null, params);
		ventanaCargar.doModal();
	}
}