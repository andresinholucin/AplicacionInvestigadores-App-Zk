package ec.edu.upse.proyinv.controlador.misproyecto;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import lombok.Getter;
import lombok.Setter;

public class VerFormularioControl {

	@Getter @Setter String valor;
	
	public VerFormularioControl(){
		System.out.println("aqui");
		Execution execution = Executions.getCurrent();
		execution.getParameter("myId");
		valor = execution.getParameter("myId");
		
	}
}
