package aplicacion.usuarios;

import java.util.*;

import aplicacion.exceptions.RepresentanteInvalidoException;

/**
 * La clase Asociacion representa una asociación de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Asociacion extends Usuario {
    /** Ciudadanos inscritos en la asociación */
    private Set<Ciudadano> ciudadanos;
    /** Asociaciones que contiene la asociación */
    private Set<Asociacion> asociaciones;
    /**  */
    private Ciudadano representante;

    public Asociacion(String nombre, String contrasena, Ciudadano representante) {
        super(nombre, contrasena);
        this.representante = representante;
        this.ciudadanos = new HashSet<Ciudadano>();
        this.ciudadanos.add(representante);
        this.asociaciones = new HashSet<Asociacion>();
    }

    public Ciudadano getRepresentante() {
        return representante;
    }

    public Set<Ciudadano> getCiudadanosDirectos() {
        return this.ciudadanos;
    }

    public Set<Ciudadano> getCiudadanos() {
        Set<Ciudadano> todosLosCiudadanos = new HashSet<>(this.ciudadanos);
        for (Asociacion a : asociaciones) {
            todosLosCiudadanos.addAll(a.getCiudadanos());
        }
        todosLosCiudadanos.addAll(getCiudadanosDirectos());
        return todosLosCiudadanos;
    }

    public Set<Asociacion> getAsociaciones() {
        return asociaciones;
    }

    public boolean comprobarCiudadano(Ciudadano ciudadano) {
        if (ciudadanos.contains(ciudadano)) {
            return true;
        }

        for (Asociacion a : asociaciones) {
            if (a.comprobarCiudadano(ciudadano) == true) {
                return true;
            }
        }

        return false;
    }

    public void inscribirCiudadano(Ciudadano ciudadano) {
        if (comprobarCiudadano(ciudadano) == true) {
            return;
        }

        ciudadanos.add(ciudadano);
    }

    public void darDeBajaCiudadano(Ciudadano ciudadano) {
        ciudadanos.remove(ciudadano);
    }

    public void anadirAsociacion(Asociacion asociacion) throws RepresentanteInvalidoException {
        if (asociacion.getRepresentante() != representante) {
            throw new RepresentanteInvalidoException("El representante de la nueva asociación debe ser el mismo.");
        }

        asociaciones.add(asociacion);
    }

    @Override
    public String toString() {
        return nombre + " <asociacion con " + getCiudadanos().size() + " ciudadanos>";
    }
}