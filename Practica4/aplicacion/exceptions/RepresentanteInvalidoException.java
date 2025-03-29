package aplicacion.exceptions;

/**
 * Excepción para cuando un respresentante no es válido.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class RepresentanteInvalidoException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción.
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public RepresentanteInvalidoException(String mensaje) {
		super(mensaje);
	}
}