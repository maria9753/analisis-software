package aplicacion;

import java.util.*;
import aplicacion.usuarios.*;
import aplicacion.exceptions.*;

public class Aplicacion {
    private Map<String, Ciudadano> ciudadanos;
    private List<Asociacion> asociaciones;
    private Map<String, Fundacion> fundaciones;

    public Aplicacion() {
        /** Constructor vacío */
    }

    public void registrarCiudadano(Ciudadano ciudadano) {
        if (ciudadanos.containsKey(ciudadano.getNif())) {
            throw new IllegalArgumentException("NIF ya registrado");
        }
        ciudadanos.put(ciudadano.getNif(), ciudadano);
    }

    public void registrarFundacion(Fundacion fundacion) {
        if (fundaciones.containsKey(fundacion.getCif())) {
            throw new IllegalArgumentException("CIF ya registrado");
        }
        fundaciones.put(fundacion.getCif(), fundacion);
    }

    public void verificarRepresentante(Asociacion asociacion) throws RepresentanteInvalidoException {
        for (Asociacion a : asociacion.getAsociaciones()) {
            if (!a.getRepresentante().equals(asociacion.getRepresentante())) {
                throw new RepresentanteInvalidoException("La longitud del nif debería ser de 9 caracteres.");
            }
        }
    }

    public void validarNif(String nif) throws NifInvalidoException {
        if (nif.length() != 9) {
            throw new NifInvalidoException("La longitud del nif debería ser de 9 caracteres.");
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

    public void validarCif(String cif) throws CifInvalidoException {
        if (cif.length() != 8) {
            throw new CifInvalidoException("La longitud del nif debería ser de 8 caracteres.");
        }
        for (int i = 0; i < 7; i++) {
            if (!Character.isDigit(cif.charAt(i))) {
                throw new CifInvalidoException("Los 7 primeros caracteres deben ser números.");
            }
        }
        if (!Character.isLetter(cif.charAt(7))) {
            throw new CifInvalidoException("El último caracter debe ser una letra.");
        }
    }

}