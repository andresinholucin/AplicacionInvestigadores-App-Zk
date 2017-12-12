package ec.edu.upse.proyinv.control.general;

import org.zkoss.zul.Textbox;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

/**
 * Clase utilitaria para gestión de google Maps

 */
//@NoArgsConstructor
public class GMaps {
	
	// Clave de Google Maps: AIzaSyDlzw2oyl1QeNM8fOEIsDB3X_73Z18PnOw
	private String key = "AIzaSyDlzw2oyl1QeNM8fOEIsDB3X_73Z18PnOw";

	// Destino donde se mostrarÃ¡n las coordenadas
	private Textbox coordenadas; 

	public String getMapaHtml(Textbox coordenadas) {
		StringBuilder contenido = new StringBuilder(); 
		
		// Valor por defecto de las coordenadas 
		if (coordenadas.getValue().isEmpty()) {
			coordenadas.setValue("lat:-2.232,lng:-80.879");
		}
		
		this.coordenadas = coordenadas; 
		
		// Clave de Google
		contenido.append("<script src='https://maps.googleapis.com/maps/api/js?key=" + this.key + "&callback=initMap' async defer></script>");
		// Estilo.
		contenido.append("<head><style> #map {height: 100%; width:100%; } </style></head>");
		// Inicio del Script 
		contenido.append("<body><div id='map'></div><script>");
		// Inicializacion del Mapa
		contenido.append("function initMap() {var map = new google.maps.Map(document.getElementById('map'), {center: {" + coordenadas.getValue() + "}, zoom: 14});");
		// Marcador
		contenido.append("var marker = new google.maps.Marker({position: {" + coordenadas.getValue() + "}, draggable: true, map: map, title: '<<Titulo>>' });");
		// Listener para el movimiento
		contenido.append("google.maps.event.addListener(marker, 'dragend', function(evt){ var txt = zk.Widget.$(jq('$" + coordenadas.getId() + "')); var val = 'lat:' + evt.latLng.lat().toFixed(6) + ', lng:' + evt.latLng.lng().toFixed(6); txt.setValue(val); txt.smartUpdate('value', val); });");
		// Finaliza el script
		contenido.append("}</script></body>"); 
		
		return contenido.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Textbox getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Textbox coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	
	
}
