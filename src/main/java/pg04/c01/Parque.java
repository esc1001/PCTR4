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
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " IN[" + contadoresPersonasPuerta.get(p) + "] OUT[" + contadoresPersonasPuerta.get(p) + "]");
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
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas de entrada debe ser igual al valor del contador del parte";
		assert sumarContadoresPuertaSalida() == contadorPersonasTotales : "INV: La suma de contadores de las puertas de salida debe ser igual al valor del contador del parte";
	}

	protected void comprobarAntesDeEntrar(){	// TODO
		//
		// TODO
		//
	}

	protected void comprobarAntesDeSalir(){		// TODO
		//
		// TODO
		//
	}


}
