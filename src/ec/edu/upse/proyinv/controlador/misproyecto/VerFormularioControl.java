package ec.edu.upse.proyinv.controlador.misproyecto;

import java.awt.Checkbox;
import java.util.ArrayList;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ListDataListener;

import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes.Array;


public class VerFormularioControl {
	
	@Wire 
	private Window ventanaVerFormulario;
	
	Button boton;
	Label etiqueta;
	Textbox textbox;
	Combobox combo;
	Radio radio;
	Checkbox checkbok;
	
    public VerFormularioControl() {
		// TODO Auto-generated constructor stub
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		// Permite enlazar los componentes que se asocian con la anotacion @Wire
		Selectors.wireComponents(view, this, false);
		
		boton=new Button();
		boton.setLabel("asdok");
		boton.setParent(ventanaVerFormulario);
		System.out.println(ventanaVerFormulario.getTitle());
	}

	
	@Command
	public void addboton(){
		boton= new Button();
		boton.setLabel("nuevoBoton");
		boton.setParent(ventanaVerFormulario);
	}
	
	@Command
	public void addetiqueta(){
		etiqueta= new Label();
		etiqueta.setValue("NuevaEtiqueta");
		etiqueta.setParent(ventanaVerFormulario);
	}
	
	@Command
	public void addtextbox(){
		textbox= new Textbox();
		textbox.setText("nueva Caja de texto");
		textbox.setParent(ventanaVerFormulario);
	}
	
	@Command
	public void addcombo(){

 		combo= new Combobox();
		combo.setText("bien");
		combo.setParent(ventanaVerFormulario);
	}
	
	@Command
	public void addradio(){
		radio=new Radio();
		radio.setLabel("este es un radio");
		radio.setParent(ventanaVerFormulario);
	
	}
	
	@Command 
	public void addchechbox(){
		checkbok = new Checkbox("hola", true);
		checkbok.setLabel("ASD");
		
		
	}
}
