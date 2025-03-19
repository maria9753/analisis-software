/**
 * La clase Asociacion representa una asociación de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Asociacion extends Usuario {
    /** Ciudadanos inscritos en la asociación*/
    private Set<Ciudadano> ciudadanos;
    /** Asociaciones que contiene la asociación*/
    private Set<Asociacion> asociaciones;
    /**  */
    private Ciudadano representante;
    
    public Asociacion(String nombre, String contrasena, Ciudadano representante, ) {
        super(nombre, contrasena);
        this.representante = representante;
        this.ciudadanos = new HashSet<Ciudadano>;
        this.ciudadanos.add(representante);
        this.Asociacion = new HashSet<Asociacion>;
    }

    public Ciudadano getRepresentante() {
        return representante;
    }

    public Set<Ciudadano> getCiudadanos() {
        return ciudadanos;
    }

    public Set<Asociacion> getAsociaciones() {
        return asociaciones;
    }

    public boolean comprobarCiudadano(Ciudadano ciudadano) {
        if (ciudadanos.contains(ciudadano)) {
            return true;
        }

        for (Asociacion a: asociaciones) {
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

    public void anadirAsociacion(Asociacion asociacion) {
        if (asociaciones.size() == 0 || asociacion.getRepresentante() != representante) {
            return;
        }

        asociaciones.add(asociacion);
    }



}