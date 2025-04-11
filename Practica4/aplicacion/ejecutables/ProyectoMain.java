package aplicacion.ejecutables;

import aplicacion.Aplicacion;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.proyectos.Proyecto;
import aplicacion.proyectos.ProyectoFundacion;
import aplicacion.usuarios.*;

/**
 * La clase ProyectoMain representa el main del apartado 2.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ProyectoMain {

    /**
     * Constructor de ProyectoTester.
     */
    public ProyectoMain() {
        /** Constructor vacio. */
    }

    /**
     * Método main que ejecuta la prueba.
     * 
     * @param args Argumentos recibidos.
     * @throws RepresentanteInvalidoException Si se asigna a una asociación un
     *                                        representante inválido.
     * @throws NifInvalidoException
     * @throws CifInvalidoException
     */
    public static void main(String[] args)
            throws RepresentanteInvalidoException, NifInvalidoException, CifInvalidoException {
        Aplicacion aplicacion = new Aplicacion();
        Ciudadano ciudadano1 = new Ciudadano("Juan Bravo", "dmcikd4", aplicacion, "01234567K");
        Ciudadano ciudadano2 = new Ciudadano("Ana Lopez", "dkcoep3", aplicacion, "01234567L");
        Ciudadano ciudadano3 = new Ciudadano("Luisa Gomez", "dkowej5", aplicacion, "01234567G");
        Fundacion fundacion = new Fundacion("Fundacion Canal", "akñsnfi7", aplicacion, "A1234567B");
        Asociacion asociacion1 = new Asociacion("conservemos el manzanares", "aksodcj4", aplicacion, ciudadano2);
        Asociacion asociacion2 = new Asociacion("amigos de los pájaros", "cdkwpnm5", aplicacion, ciudadano2);
        asociacion2.inscribirCiudadano(ciudadano2, null);
        asociacion2.inscribirCiudadano(ciudadano3, null);
        asociacion1.inscribirCiudadano(ciudadano1, null);
        asociacion1.anadirAsociacion(asociacion2);

        Proyecto proyecto1 = new Proyecto("Limpieza del manzanares", "Se quiere hacer una limpieza del río manzanares",
                asociacion1);
        ProyectoFundacion proyecto2 = new ProyectoFundacion("Gastemos menos agua",
                "Se quiere tartar de gastar menos agua en las casas", fundacion, 100000.0, 80.0);

        System.out.println("[" + proyecto1.toString() + ",\n " + proyecto2.toString() + "]");
    }
}
