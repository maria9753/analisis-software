package aplicacion.exceptions;

/**
 * Excepción para cuando un ciudadano o asociación ya ha apoyado un proyecto y quiere apoyarlo.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class ProyectoYaApoyadoException extends Exception {
    /** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción.
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public ProyectoYaApoyadoException(String mensaje) {
		super(mensaje);
	}
}
