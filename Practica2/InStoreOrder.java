package orders;
public class InStoreOrder extends Order {
private BookStore store;
public InStoreOrder(String item, double price, String customer, BookStore store){
super(item, price, customer);
this.store = store;
}
@Override
public String toString() {
return super.toString()+"\nStore: "+this.store;
}
}