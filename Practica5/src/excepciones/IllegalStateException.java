package src.excepciones;

/**
 * Excepción para cuando un estado no es válido.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class IllegalStateException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción.
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public IllegalStateException(String mensaje) {
		super(mensaje);
	}
}