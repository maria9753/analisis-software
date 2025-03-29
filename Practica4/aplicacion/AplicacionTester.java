package aplicacion;

import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.usuarios.*;

public class AplicacionTester {

    public AplicacionTester() {
        /** Constructor vacio. */
    }

    public static void main(String[] args) throws RepresentanteInvalidoException {
        Aplicacion aplicacion = new Aplicacion();
        Ciudadano ciudadano1 = new Ciudadano("Juan Bravo", "dmcikd4", "01234567K");
        Ciudadano ciudadano2 = new Ciudadano("Ana Luisa", "dkcoep3", "01234567L");
        Ciudadano ciudadano3 = new Ciudadano("Luisa Gomez", "dkowej5", "01234567G");
        Fundacion fundacion = new Fundacion("Fundacion Canal", "akñsnfi7", "A1234567B");
        Asociacion asociacion1 = new Asociacion("conservemos el manzanares", "aksodcj4", ciudadano2);
        Asociacion asociacion2 = new Asociacion("amigos de los pájaros", "cdkwpnm5", ciudadano2);
        asociacion2.inscribirCiudadano(ciudadano3);
        asociacion1.inscribirCiudadano(ciudadano1);
        asociacion1.anadirAsociacion(asociacion2);

        System.out.println("[" + ciudadano1.toString() + "\n" + ciudadano2.toString() + "\n" + ciudadano3.toString()
                + "\n" + asociacion1.toString() + "\n" + asociacion2.toString() + "\n" + fundacion.toString() + "]");
    }
}
