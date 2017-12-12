package ec.edu.upse.proyinv.control.general;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import ec.edu.upse.proyinv.modelo.Opcion;
import ec.edu.upse.proyinv.modelo.OpcionPrivilegio;
import ec.edu.upse.proyinv.modelo.Dao.OpcionDAO;


public class Menu {

	/**
	 * Provee los datos para las opciones
	 * @return
	 */
	public List<Opcion> getOpciones() {
		OpcionDAO opcionDao = new OpcionDAO();
		return opcionDao.getOpciones();
	}

	/**
	 * Se ejecuta al dar click en la fila del grid
	 * @param url
	 */
	@Command
	@NotifyChange("formularioActual")
	public void cargaUrl(@BindingParam("opcion") Opcion opcion) {
		boolean tienePrivilegio = false;

		for (OpcionPrivilegio opcionPrivilegio : opcion.getOpcionPrivilegios()) {

			// Utilidad que permite verificar los privilegios del usuario actual.
			if (SecurityUtil.isAnyGranted(opcionPrivilegio.getPrivilegio().getCodigo())) {
				tienePrivilegio = true;
				break; 
			}
		}

		if (tienePrivilegio) {
			// Si comienza con "http" se entiende que es una URL
			// de lo contrario es un formulario.
			if (opcion.getUrl().substring(0, 4).toLowerCase().equals("http")) {
				
				// Limpia el atributo del formulario actual de la variable 
				// de sesion
				Sessions.getCurrent().setAttribute("FormularioActual", null);
				Executions.getCurrent().sendRedirect(opcion.getUrl(), "_blank");	
				
			}else{
				// Coloca el atributo del formulario actual en una variable 
				// de sesion
				Sessions.getCurrent().setAttribute("FormularioActual", opcion);
			}
		}else{
			Clients.showNotification("No tiene privilegios para acceder a esta opcion.");
		}

	}

	/**
	 * Retorna el formulario de trabajo actual, tomado desde una variable de sesion.
	 * @return
	 */
	public String getFormularioActual() {
		String url = null; 
		// Recupera el formulario actual desde la variable de sesion
		if (Sessions.getCurrent().getAttribute("FormularioActual") != null) {
			url = ((Opcion)Sessions.getCurrent().getAttribute("FormularioActual")).getUrl();
		}
		return url; 
	}

	/**
	 * Retorna el nombre del usuario logoneado.
	 * @return
	 */
	public String getNombreUsuario() {
		return SecurityUtil.getUser().getUsername();
	}

}