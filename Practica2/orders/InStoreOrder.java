package orders;
public class InStoreOrder extends Order{
    private BookStore store;
    
    public InStoreOrder(String item, double price, String customer, BookStore store){
        super(item, price, customer);
            this.store = store;
    }

    @Override
    public String toString() {
        return super.toString()+"\nStore: "+this.store;
    }

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