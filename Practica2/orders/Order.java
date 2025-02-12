package orders;
public abstract class Order {
    private String item;
    private double basePrice;
    private String customer;

    public Order(String item, double price, String customer) {
        this.item = item;
        this.basePrice = price;
        this.customer = customer;
    }

    public double totalPrice() {
        return this.basePrice;
    }

    public double setDiscount(double percentaje){
        if (percentaje >= 100){
            percentaje = 100;
        }
        else if (percentaje <= 0){
            percentaje = 0;
        }
        return this.basePrice - (this.basePrice*percentaje/100);
    }
    
    
    @Override
    public String toString() {
        return "Order of '"+this.item+"' for "+this.customer+" ("+this.totalPrice()+"$)";
    }
}

public class Main {
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
                System.out.println("With 5.0% discount on base price:\n"+
                                    o+
                                    "\n---------------------");
            }
        }
    }
}
