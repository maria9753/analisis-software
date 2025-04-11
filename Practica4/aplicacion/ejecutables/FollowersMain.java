package aplicacion.ejecutables;

import aplicacion.proyectos.*;
import aplicacion.*;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.exceptions.ProponenteNoApoyaException;
import aplicacion.exceptions.ProyectoMasDe60Exception;
import aplicacion.exceptions.ProyectoYaApoyadoException;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.usuarios.*;

public class FollowersMain {

    public FollowersMain() {
        /** Constructor vacío */
    }

    /**
     * Método main que ejecuta la prueba.
     * 
     * @param args Argumentos recibidos.
     * @throws RepresentanteInvalidoException Si se asigna a una asociación un
     *                                        representante inválido.
     * @throws NifInvalidoException
     * @throws CifInvalidoException
     * @throws ProyectoYaApoyadoException 
     * @throws ProyectoMasDe60Exception 
     * @throws ProponenteNoApoyaException 
     */
    public static void main(String[] args) throws RepresentanteInvalidoException, NifInvalidoException, CifInvalidoException, ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
		Aplicacion aplicacion = new Aplicacion();
		
		Ciudadano juan = new Ciudadano("Juan Bravo", "dmcikd4", aplicacion, "01234567K");
		Ciudadano ana = new Ciudadano("Ana López", "dkcoep3", aplicacion, "01234567L");
		Ciudadano luisa = new Ciudadano("Luisa Gómez", "dkowej5", aplicacion, "01234567G");
		
		Fundacion fundacionCanal = new Fundacion("Fundación Canal", "akñsnfi7", aplicacion, "A1234567B");
		
		Asociacion amigosPajaros = new Asociacion("amigos de los pájaros", "cdkwpnm5", aplicacion, ana);
		Asociacion conservemosManzanares = new Asociacion("conservemos el manzanares", "aksodcj4", aplicacion, ana);
		
		
		amigosPajaros.inscribirCiudadano(ana, null);   
		amigosPajaros.inscribirCiudadano(luisa, null);
		
		conservemosManzanares.inscribirCiudadano(juan, null);
		conservemosManzanares.anadirAsociacion(amigosPajaros);
		
		juan.startToFollow(fundacionCanal, null);
		
		Proyecto limpiezaManzanares = new Proyecto ("Limpieza del manzanares", "Limpiar el manzanares para recuperar su flora y fauna", conservemosManzanares);
				
		conservemosManzanares.proponerProyecto(limpiezaManzanares);
		
		ProyectoFundacion gastarMenosAgua = new ProyectoFundacion("Gastemos menos agua", "Mejora de las infraestructuras de distribución y captación de agua", fundacionCanal, 1000000.0, 80.0);

		fundacionCanal.proponerProyecto(gastarMenosAgua);
		
		System.out.println("Anuncios para Juan Bravo:");
		System.out.println(juan.getMensajesAnuncios());
		System.out.println("\n");
		
		System.out.println("Anuncios para Ana López:");
		System.out.println(ana.getMensajesAnuncios());
		System.out.println("\n");
		
		System.out.println("Anuncios para Luisa Gómez:");
		System.out.println(luisa.getMensajesAnuncios());
	}
}
