package orders;
/**
 * La clase InStoreOrder representa los pedidos en tienda de una cadena de librerías.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class InStoreOrder extends Order{
    /**Librería en la que se ha realizado el pedido*/
    private BookStore store;
    
    /**
	 * Constructor de la clase InStoreOrder.
	 * 
	 * @param item 			Item del pedido.
	 * @param price 	    Precio base del pedido.
     * @param customer 	    Nombre del cliente.
     * @param store 	    Librería en la que se ha realizado el pedido.
	 */
    public InStoreOrder(String item, double price, String customer, BookStore store){
        super(item, price, customer);
            this.store = store;
    }

    /**
	 * Método para generar pedidos en tienda.
	 * 
	 * @return Una cadena que contiene los detalles de los pedidos en tienda.
	 */
    @Override
    public String toString() {
        return super.toString()+"\nStore: "+this.store;
    }

    /**
	 * Método para calcular el precio total de un pedido en tienda. Se aplican 
     * descuentos según la tienda en la que se realizó el pedido, las opciones son:
     * 1$ en MAIN, 2$ en BROOKLYN, y 5$ en NEWARK.
	 * 
	 * @return Número que representa el precio total de un pedido en tienda.
	 */
    @Override
    public double totalPrice() {
        if (this.store == BookStore.BROOKLYN){
            return super.totalPrice() - 1;
        }
        else if (this.store == BookStore.NEWARK){
            return super.totalPrice() - 2;
        }
        return super.totalPrice();
    }
}
