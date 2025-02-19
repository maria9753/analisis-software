package orders;
/**
 * La enumeración BookStore representa los posibles librerías físicas donde 
 * se pueden realizar pedidos en tienda.  
 * 
 * @author Carmen Gómez, María Pozo.
 */
public enum BookStore {
    /** Librería principal ubicada en Manhattan, NYC*/
    MAIN("Fifth Avenue. 73, Manhattan, NYC"),
    /** Librería ubicada en Brooklyn, NYC*/
    BROOKLYN("Bedford Avenue 24, Brooklyn, NYC"),
    /** Librería principal ubicada en Newark, NJ*/
    NEWARK("Broad Street 11, Newark, NJ");

    /** Precio base del pedido*/
    private String address;

     /**
     * Método constructor para asignar la dirección a cada librería.
     *
     * @param address       Dirección de la librería.
     */
    private BookStore(String address) {
        this.address = address;
    }
    
    /**
	 * Método para generar la dirección de la librería.
	 * 
	 * @return Una cadena que contiene la dirección de la librería.
	 */
    public String toString() {
        return this.address;
    }
}
