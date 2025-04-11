package aplicacion.ejecutables;

import aplicacion.Aplicacion;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.usuarios.*;

/**
 * La clase AplicacionTester representa el test de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class AplicacionMain {
    /**
     * Contructor de la clase AplicacionTester.
     */
    public AplicacionMain() {
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
        Ciudadano ciudadano2 = new Ciudadano("Ana Luisa", "dkcoep3", aplicacion, "01234567L");
        Ciudadano ciudadano3 = new Ciudadano("Luisa Gomez", "dkowej5", aplicacion, "01234567G");
        Fundacion fundacion = new Fundacion("Fundacion Canal", "akñsnfi7", aplicacion, "A1234567B");
        Asociacion asociacion1 = new Asociacion("conservemos el manzanares", "aksodcj4", aplicacion, ciudadano2);
        Asociacion asociacion2 = new Asociacion("amigos de los pájaros", "cdkwpnm5", aplicacion, ciudadano2);
        asociacion2.inscribirCiudadano(ciudadano3, null);
        asociacion1.inscribirCiudadano(ciudadano1, null);
        asociacion1.anadirAsociacion(asociacion2);

        System.out.println("[" + ciudadano1.toString() + "\n" + ciudadano2.toString() + "\n" + ciudadano3.toString()
                + "\n" + asociacion1.toString() + "\n" + asociacion2.toString() + "\n" + fundacion.toString() + "]");
    }
}
