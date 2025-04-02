package aplicacion;

import java.util.*;
import aplicacion.usuarios.*;
import aplicacion.exceptions.RepresentanteInvalidoException;
import aplicacion.proyectos.Proyecto;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;

/**
 * La clase Aplicacion representa la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Aplicacion {
    /** Mapa de ciudadanos y nif */
    private Map<String, Ciudadano> ciudadanos;
    /** Lista de asociaciones de la aplicación */
    private List<Asociacion> asociaciones;
    /** Mapa de fundaciones y cif */
    private Map<String, Fundacion> fundaciones;
    /** Lista de proyectos propuestos */
    private List<Proyecto> proyectos;

    /**
     * Constructor de la clase Aplicacion.
     * Inicializa las estructuras de datos necesarias.
     */
    public Aplicacion() {
        this.ciudadanos = new HashMap<>();
        this.asociaciones = new ArrayList<>();
        this.fundaciones = new HashMap<>();
        this.proyectos = new ArrayList<>();
    }

    /**
     * Registra un ciudadano en la aplicación.
     * 
     * @param ciudadano El ciudadano a registrar.
     * @throws IllegalArgumentException Si el NIF ya está registrado.
     */
    public void registrarCiudadano(Ciudadano ciudadano) {
        if (ciudadanos.containsKey(ciudadano.getNif())) {
            throw new IllegalArgumentException("NIF ya registrado");
        }
        ciudadanos.put(ciudadano.getNif(), ciudadano);
    }

    /**
     * Registra un ciudadano en la aplicación.
     * 
     * @param ciudadano El ciudadano a registrar.
     */
    public void registrarAsociacion(Asociacion asociacion) {
        asociaciones.add(asociacion);
    }

    /**
     * Registra una fundación en la aplicación.
     * 
     * @param fundacion La fundación a registrar.
     * @throws IllegalArgumentException Si el CIF ya está registrado.
     */
    public void registrarFundacion(Fundacion fundacion) {
        if (fundaciones.containsKey(fundacion.getCif())) {
            throw new IllegalArgumentException("CIF ya registrado");
        }
        fundaciones.put(fundacion.getCif(), fundacion);
    }

    /**
     * Verifica que todas las asociaciones dentro de una asociación tengan el mismo
     * representante.
     * 
     * @param asociacion La asociación a verificar.
     * @throws RepresentanteInvalidoException Si alguna asociación tiene un
     *                                        representante diferente.
     */
    public void verificarRepresentante(Asociacion asociacion) throws RepresentanteInvalidoException {
        for (Asociacion a : asociacion.getAsociaciones()) {
            if (!a.getRepresentante().equals(asociacion.getRepresentante())) {
                throw new RepresentanteInvalidoException("La longitud del nif debería ser de 9 caracteres.");
            }
        }
    }

    /**
     * Valida que un NIF tenga el formato correcto.
     * 
     * @param nif El NIF a validar.
     * @throws NifInvalidoException Si el NIF no cumple con el formato requerido.
     */
    public void validarNif(String nif) throws NifInvalidoException {
        if (nif.length() != 8) {
            throw new NifInvalidoException("La longitud del nif debería ser de 8 caracteres.");
        }
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(nif.charAt(i))) {
                throw new NifInvalidoException("Los 8 primeros caracteres deben ser números.");
            }
        }
        if (!Character.isLetter(nif.charAt(8))) {
            throw new NifInvalidoException("El último caracter debe ser una letra.");
        }
    }

    /**
     * Valida que un CIF tenga el formato correcto.
     * 
     * @param cif El CIF a validar.
     * @throws CifInvalidoException Si el CIF no cumple con el formato requerido.
     */
    public void validarCif(String cif) throws CifInvalidoException {
        if (cif.length() != 8) {
            throw new CifInvalidoException("La longitud del nif debería ser de 8 caracteres.");
        }

        if (!Character.isLetter(cif.charAt(0))) {
            throw new CifInvalidoException("El primer caracter debe ser una letra.");
        }

        for (int i = 1; i < 7; i++) {
            if (!Character.isDigit(cif.charAt(i))) {
                throw new CifInvalidoException("Los 6 siguientes caracteres deben ser números.");
            }
        }
        if (!Character.isLetter(cif.charAt(7))) {
            throw new CifInvalidoException("El último caracter debe ser una letra.");
        }
    }

    /**
     * Obtiene un ciudadano por su nombre.
     * 
     * @param nombre El nombre del ciudadano.
     * @return El ciudadano encontrado o null si no existe.
     */
    public Ciudadano obtenerCiudadanoPorNombre(String nombre) {
        for (Ciudadano ciudadano : ciudadanos.values()) {
            if (ciudadano.getNombre().equals(nombre)) {
                return ciudadano;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los ciudadanos registrados en la aplicación.
     * 
     * @return Una lista con todos los ciudadanos registrados.
     */
    public List<Ciudadano> obtenerTodosLosCiudadanos() {
        return new ArrayList<>(ciudadanos.values());
    }

    /**
     * Obtiene un proyecto por su nombre.
     * 
     * @param nombre El nombre del proyecto.
     * @return El proyecto encontrado o null si no existe.
     */
    public Proyecto obtenerProyectoPorNombre(String nombre) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre().equals(nombre)) {
                return proyecto;
            }
        }
        return null;
    }

    /**
     * Obtiene un proyecto por su código único.
     * 
     * @param codigo El código del proyecto.
     * @return El proyecto encontrado o null si no existe.
     */
    public Proyecto obtenerProyectoPorCodigo(int codigo) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getCodigo() == codigo) {
                return proyecto;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los proyectos registrados en la aplicación.
     * 
     * @return Una lista con todos los proyectos registrados.
     */
    public List<Proyecto> obtenerTodosLosProyectos() {
        return new ArrayList<>(proyectos);
    }

    /**
     * Propone un proyecto en la aplicación.
     * @param proyecto El proyecto que se propone.
     */
    public void proponerProyecto(Proyecto proyecto) {
        proyectos.add(proyecto);
    }

    /**
     * Obtiene los proyectos de la aplicación y su número de apoyos.
     * 
     * @return Un mapa con los proyectos y su número de apoyos.
     */
    public Map<Proyecto, Integer> obtenerProyectosConApoyosOrdenados() {
        Map<Proyecto, Integer> apoyos = new HashMap<>();
        List<Proyecto> ordenados = new ArrayList<>();

        for (Ciudadano c: ciudadanos.values()) {
            for (Proyecto p: c.getProyectosApoyados().keySet()) {
                apoyos.put(p, apoyos.getOrDefault(p, 0) + 1);
            }
        }

        for (Asociacion a: asociaciones) {
            for (Proyecto p: a.getProyectosApoyados().keySet()) {
                apoyos.put(p, apoyos.getOrDefault(p, 0) + a.getCiudadanos().size());
            }
        }

        ordenados.addAll(apoyos.keySet());
        int i, j;
        for (i = ordenados.size() - 1; i > 0; i--) {
            for (j = 0; j < i; j++) {
                if (apoyos.get(ordenados.get(j)) > apoyos.get(ordenados.get(j+1))) {
                    Collections.swap(ordenados, j, j+1);
                } 
            }
        }

        Map<Proyecto, Integer> resultado = new LinkedHashMap<>();
        for (Proyecto p: ordenados) {
            resultado.put(p, apoyos.get(p));
        }

        return resultado;
    }

    /**
     * Obtiene los proyectos de la aplicación y los ciudadanos que los apoyan.
     * 
     * @return Un mapa con los proyectos y los ciudadanos que los apoyan.
     */
    public Map<Proyecto, Set<Ciudadano>> obtenerProyectosYCiudadanosQueLosApoyan() {
        Map<Proyecto, Set<Ciudadano>> proyectosYciudadanos = new HashMap<>();

        for (Ciudadano c: ciudadanos.values()) {
            for (Proyecto p: c.getProyectosApoyados().keySet()) {
                if (!proyectosYciudadanos.containsKey(p)) {
                    proyectosYciudadanos.put(p, new HashSet<Ciudadano>());
                }

                proyectosYciudadanos.get(p).add(c);
            }
        }

        for (Asociacion a: asociaciones) {
            for (Proyecto p: a.getProyectosApoyados().keySet()) {
                if (!proyectosYciudadanos.containsKey(p)) {
                    proyectosYciudadanos.put(p, new HashSet<Ciudadano>());
                }

                proyectosYciudadanos.get(p).addAll(a.getCiudadanos());
            }
        }

        return proyectosYciudadanos;
    }

}
