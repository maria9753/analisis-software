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
        if (this.store.equal(BookStore.BROOKLYN)){
            return this.basePrice - 1;
        }
        else if (this.store.equal(BookStore.NEWARK)){
            return this.basePrice - 2;
        }
        return this.basePrice;
    }
}