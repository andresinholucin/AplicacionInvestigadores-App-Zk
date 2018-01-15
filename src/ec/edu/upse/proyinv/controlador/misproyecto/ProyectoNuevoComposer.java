package ec.edu.upse.proyinv.controlador.misproyecto;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class ProyectoNuevoComposer extends SelectorComposer<Component> {
	private static final long serialVersionUID = 8243942703081449079L;
	
	@Wire
	private ProyectoNuevoControl proyectonuevo;
	
	@Wire
	private ProyectoNuevoControl interfacenuevo;
	
	@Wire
	private Window winProyectoNuevo;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
			
	};
	
	@Listen("onClick=#salir")
	public void salirventana(){
		winProyectoNuevo.detach();
	}
	

	
}
