package orders;
public class OnlineOrder extends Order {
    
    public OnlineOrder(String item, double price, String customer, String email){
        super(item, price, customer);
            this.email = email;
    }

    @Override
    public String toString() {
        return super.toString()+"\nEmail: "+this.email;
    }

    @Override
    public double totalPrice() {
        return this.basePrice + 5;
    }
}