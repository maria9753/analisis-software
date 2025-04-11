package aplicacion.ejecutables;

import aplicacion.Aplicacion;
import aplicacion.anuncios.UnoDeCadaNStrategy;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.proyectos.Proyecto;
import aplicacion.proyectos.ProyectoFundacion;
import aplicacion.usuarios.Asociacion;
import aplicacion.usuarios.Ciudadano;
import aplicacion.usuarios.Fundacion;

/**
 * La clase StrategyMain representa el main del apartado 5.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class StrategyMain {
	
	public StrategyMain() {
		/** Contructor vacío. */
	}
	
	/**
	 * Método main que ejecuta la prueba.
	 * @param args Argumentos recibidos.
	 * @throws NifInvalidoException
	 * @throws RepresentanteInvalidoException
	 * @throws CifInvalidoException
	 */
	public static void main(String[] args) throws NifInvalidoException, RepresentanteInvalidoException, CifInvalidoException {
		Aplicacion aplicacion = new Aplicacion();
		
		Ciudadano juan = new Ciudadano("Juan Bravo", "dmcikd4", aplicacion, "01234567K");
		Ciudadano ana = new Ciudadano("Ana López", "dkcoep3", aplicacion, "01234567L");
		Ciudadano luisa = new Ciudadano("Luisa Gómez", "dkowej5", aplicacion, "01234567G");
		
		Fundacion fundacionCanal = new Fundacion("Fundación Canal", "akñsnfi7", aplicacion, "A1234567B");
		
		Asociacion amigosPajaros = new Asociacion("amigos de los pájaros", "cdkwpnm5", aplicacion, ana);
		Asociacion conservemosManzanares = new Asociacion("conservemos el manzanares", "aksodcj4", aplicacion, ana);
		
		
		amigosPajaros.inscribirCiudadano(ana, null);   
		amigosPajaros.inscribirCiudadano(luisa, null);
				
		conservemosManzanares.inscribirCiudadano(juan, new UnoDeCadaNStrategy(2));
		conservemosManzanares.anadirAsociacion(amigosPajaros);
		
		juan.startToFollow(fundacionCanal, new UnoDeCadaNStrategy(2));
		
		Proyecto limpiezaManzanares = new Proyecto ("Limpieza del manzanares", "Limpiar el manzanares para recuperar su flora y fauna", conservemosManzanares);
				
		conservemosManzanares.proponerProyecto(limpiezaManzanares);
		
		ProyectoFundacion gastarMenosAgua = new ProyectoFundacion("Gastemos menos agua", "Mejora de las infraestructuras de distribución y captación de agua", fundacionCanal, 1000000.0, 80.0);

		fundacionCanal.proponerProyecto(gastarMenosAgua);

		System.out.println(juan.getMensajesAnuncios());
	}
}
