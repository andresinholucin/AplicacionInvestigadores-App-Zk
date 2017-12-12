package ec.edu.upse.proyinv.controlador;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class GenerarKeyControl {
	private String textokey;
	
	@Command
	@NotifyChange({"textokey"})
	public void generarkey(){
		System.out.println("aqio");
		setTextokey(creaPass());
		
	}
	
	char[] elementos={'0','1','2','3','4','5','6','7','8','9' ,'a',
			'b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t',
			'u','v','w','x','y','z'};

	char[] conjunto = new char[15];
	String pass;
			
	public String creaPass(){
				
				for(int i=0;i<15;i++){
					int el = (int)(Math.random()*37);
					conjunto[i] = (char)elementos[el];
				}
				return pass = new String(conjunto);
				
	}

	public String getTextokey() {
		return textokey;
	}

	public void setTextokey(String textokey) {
		this.textokey = textokey;
	}
	
	
}
