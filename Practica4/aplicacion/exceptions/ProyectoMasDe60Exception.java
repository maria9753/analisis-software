package aplicacion.exceptions;

/**
 * Excepción para cuando un ciudadano o asociación quiere apoyar un proyecto que se creó hace más de 60 días.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class ProyectoMasDe60Exception extends Exception {
    /** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción.
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public ProyectoMasDe60Exception(String mensaje) {
		super(mensaje);
	}
}
