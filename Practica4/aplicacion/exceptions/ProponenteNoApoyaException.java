package aplicacion.exceptions;

/**
 * Excepción para cuando un ciudadano o asociación quiere apoyar un proyecto que ha propuesto.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class ProponenteNoApoyaException extends Exception {
    /** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción.
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public ProponenteNoApoyaException(String mensaje) {
		super(mensaje);
	}
}
