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
        this.basePrice= this.basePrice - (this.basePrice*percentaje/100);
        return this.basePrice;
    }
    
    
    @Override
    public String toString() {
        return "Order of '"+this.item+"' for "+this.customer+" ("+this.totalPrice()+"$)";
    }
}
