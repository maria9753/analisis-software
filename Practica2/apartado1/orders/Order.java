package orders;
/**
 * La clase representa los pedidos de una cadena de librerías. 
 * La clase Order representa los pedidos. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Order {
    /** Item del pedido*/
    private String item;
    /** Precio base del pedido*/
    private double basePrice;
    /** Nombre del cliente*/
    private String customer;

    /**
	 * Constructor de la clase Order.
	 * 
	 * @param item 			Item del pedido.
	 * @param price 	    Precio base del pedido.
     * @param customer 	    Nombre del cliente.
	 */
    public Order(String item, double price, String customer) {
        this.item = item;
        this.basePrice = price;
        this.customer = customer;
    }

    /**
	 * Método para calcular el precio total del pedido.
	 * 
	 * @return Número que representa el precio total del pedido.
	 */
    public double totalPrice() {
        return this.basePrice;
    }

    /**
	 * Método para calcular el descuento aplicado al pedido.
	 * 
     * @param percentaje    Porcentaje de descuento al pedido.
	 * @return Número que representa el precio que se va a descontar al pedido.
	 */
    public double setDiscount(double percentaje){
        if (percentaje >= 100){
            percentaje = 100;
        }
        else if (percentaje <= 0){
            percentaje = 0;
        }
        this.basePrice= this.basePrice - (this.basePrice*percentaje/100);
        return this.basePrice;
    }
    
    /**
	 * Método para generar pedidos.
	 * 
	 * @return Una cadena que contiene los detalles del pedido.
	 */
    @Override
    public String toString() {
        return "Order of '"+this.item+"' for "+this.customer+" ("+this.totalPrice()+"$)";
    }
}
