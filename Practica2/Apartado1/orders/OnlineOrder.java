package orders;
/**
 * La clase OnlineOrder representa los pedidos online de una cadena de librerías.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class OnlineOrder extends Order {
    /**Email del cliente que ha realizado el pedido online*/
    private String email;
    /**Recargo adicional de los pedidos online*/
    private static final double FEE = 5.0;
    
    /**
	 * Constructor de la clase OnlineOrder.
	 * 
	 * @param item 			Item del pedido.
	 * @param price 	    Precio base del pedido.
     * @param customer 	    Nombre del cliente.
     * @param email 	    Email del cliente.
	 */
    public OnlineOrder(String item, double price, String customer, String email){
        super(item, price, customer);
            this.email = email;
    }

    /**
	 * Método para generar pedidos online.
	 * 
	 * @return Una cadena que contiene los detalles de los pedidos online.
	 */
    @Override
    public String toString() {
        return super.toString()+"\nEmail: "+this.email;
    }

    /**
	 * Método para calcular el precio total de un pedido online. Estos pedidos
     * tienen un recargo de 5$.
	 * 
	 * @return Número que representa el precio total de un pedido online.
	 */
    @Override
    public double totalPrice() {
        return super.totalPrice() + FEE;
    }
}
