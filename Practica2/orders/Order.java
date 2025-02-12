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
    
    @Override
    public String toString() {
        return "Order of '"+this.item+"' for "+this.customer+" ("+this.totalPrice()+"$)";
    }
}
