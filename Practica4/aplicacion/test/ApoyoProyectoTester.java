package aplicacion.test;

import java.util.*;

import aplicacion.Aplicacion;
import aplicacion.exceptions.ProponenteNoApoyaException;
import aplicacion.exceptions.ProyectoMasDe60Exception;
import aplicacion.exceptions.ProyectoYaApoyadoException;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.proyectos.Proyecto;
import aplicacion.proyectos.ProyectoFundacion;
import aplicacion.usuarios.*;

public class ApoyoProyectoTester {
    /**
     * Constructor de ApoyoProyectoTester.
     */
    public ApoyoProyectoTester() {
        /** Constructor vacio. */
    }

    /**
     * Método main que ejecuta la prueba.
     * 
     * @param args Argumentos recibidos.
     * @throws RepresentanteInvalidoException 
     * @throws ProyectoYaApoyadoException 
     * @throws ProyectoMasDe60Exception 
     * @throws ProponenteNoApoyaException 
     */
    public static void main(String[] args) throws RepresentanteInvalidoException, ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        Aplicacion app = new Aplicacion();
        Ciudadano ciudadano1 = new Ciudadano("Juan Bravo", "dmcikd4", "01234567K");
        Ciudadano ciudadano2 = new Ciudadano("Ana Luisa", "dkcoep3", "01234567L");
        Ciudadano ciudadano3 = new Ciudadano("Luisa Gomez", "dkowej5", "01234567G");
        Fundacion fundacion = new Fundacion("Fundacion Canal", "akñsnfi7", "A1234567B");
        Asociacion asociacion1 = new Asociacion("conservemos el manzanares", "aksodcj4", ciudadano2);
        Asociacion asociacion2 = new Asociacion("amigos de los pájaros", "cdkwpnm5", ciudadano2);
        asociacion2.inscribirCiudadano(ciudadano3);
        asociacion1.inscribirCiudadano(ciudadano1);
        asociacion1.anadirAsociacion(asociacion2);
        Proyecto proyecto1 = new Proyecto("Limpieza del manzanares", "Se quiere hacer una limpieza del río manzanares",
                asociacion1);
        ProyectoFundacion proyecto2 = new ProyectoFundacion("Gastemos menos agua",
                "Se quiere tartar de gastar menos agua en las casas", fundacion, 100000.0, 80.0);

        app.registrarCiudadano(ciudadano1);
        app.registrarCiudadano(ciudadano2);
        app.registrarCiudadano(ciudadano3);
        app.registrarAsociacion(asociacion1);
        app.registrarAsociacion(asociacion2);
        app.registrarFundacion(fundacion);
        app.proponerProyecto(proyecto1);
        app.proponerProyecto(proyecto2);

        ciudadano1.apoyarProyecto(proyecto2);        

        Map<Proyecto, Integer> mapa1 = app.obtenerProyectosConApoyosOrdenados();
        Map<Proyecto, Set<Ciudadano>> mapa2 = app.obtenerProyectosYCiudadanosQueLosApoyan();

        int i = 0;
        System.out.printf("{");
        for (Map.Entry<Proyecto, Integer> entry: mapa1.entrySet()) {
            System.out.println(i + ": " + entry.getKey() + "=" + entry.getValue());
            i++;
        }
        System.out.printf("}");


        int j = 0;
        System.out.printf("{");
        for (Map.Entry<Proyecto, Set<Ciudadano>> entry: mapa2.entrySet()) {
            System.out.println(j + ": " + entry.getKey() + "=" + entry.getValue());
            j++;
        }
        System.out.printf("}");

    }
}
