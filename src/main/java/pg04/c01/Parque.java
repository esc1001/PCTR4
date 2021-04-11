package pg04.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{

	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private Hashtable<String, Integer> contadoresPersonasPuertaSalida;
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		contadoresPersonasPuertaSalida = new Hashtable<String, Integer>();
	}

	@Override
	public void entrarAlParque(String puerta){
		comprobarAntesDeEntrar();
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
	}
	
	@Override
	public void salirDelParque(String puerta) {
		comprobarAntesDeSalir();
		// Si no hay salidas por esa puerta, inicializamos
		if (contadoresPersonasPuertaSalida.get(puerta) == null){
			contadoresPersonasPuertaSalida.put(puerta, 0);
		}
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuertaSalida.put(puerta, contadoresPersonasPuertaSalida.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		
	}
	
	private void imprimirInfo (String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales);
		
		Integer contIN, contOUT;
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()) {
			contIN = contadoresPersonasPuerta.get(p);
			if ( contIN == null ) {
				contIN = 0;
			}
			contOUT = contadoresPersonasPuertaSalida.get(p);
			if ( contOUT == null ) {
				contOUT = 0;
			}
			System.out.println("----> Por puerta " + p + " IN[" + contIN + "] OUT[" + contOUT + "]");
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	private int sumarContadoresPuertaSalida() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuertaSalida.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() >= contadorPersonasTotales : "INV: La suma de contadores de las puertas de entrada debe ser mayor o igual al valor del contador del parque";
		assert sumarContadoresPuertaSalida() >= sumarContadoresPuerta() : "INV: La suma de contadores de las puertas de salida debe ser mayor o igual a la suma de contadores de las puertas de entrada";
		assert sumarContadoresPuerta() - sumarContadoresPuertaSalida() == contadorPersonasTotales : "INV: La suma de contadores de las puertas de entrada menos la suma de contadores de las puertas de salida debe ser igual al valor del contador del parque";
	}

	protected synchronized void comprobarAntesDeEntrar() {
		checkInvariante();
		notifyAll();
	}

	protected synchronized void comprobarAntesDeSalir() {
		checkInvariante();
		while ( contadorPersonasTotales <= 0 ) {
			try { 
				wait();
			} catch (InterruptedException e) {
			}
		}
	}


}
