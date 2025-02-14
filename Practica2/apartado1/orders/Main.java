package orders;
/**
 * Es la clase principal que ejecuta el programa para gestionar los pedidos 
 * de una cadena de librerías. Se ejecuta un programa de prueba donde se realizan
 * dos pedidos en tienda y uno online.
 * La clase Order representa los pedidos. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Main {
    /**
     * Constructor de la clase Main. No realiza ninguna acción.
     */
    public Main() {
        // Constructor vacío
    }

    /**
	 * Método principal, donde se crean dos pedidos en tienda y uno online. Después
     * se aplica un descuento del 5% a los pedidos superiores al 50$.
	 * 
	 * @param args 		Los argumentos de la línea de comandos
	 */
    
    public static void main(String[] args) {
        Order[] orders = {
            new OnlineOrder("The Java bible", 50, "Peter Smith", "smith@gmail.com"),
            new InStoreOrder("Basic programming", 30, "Sue Rogers", BookStore.MAIN),
            new InStoreOrder("Functional Pearls", 50, "Donald Knuth", BookStore.NEWARK),
        };
        
        for (Order o: orders) {
            System.out.println(o+"\n---------------------");
            if (o.totalPrice()>=50) {
                o.setDiscount(5.0);
                System.out.println("With 5.0% discount on base price:\n" + o + "\n---------------------");
            }
        }
    }
}
