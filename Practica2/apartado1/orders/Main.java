package orders;
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
                System.out.println("With 5.0% discount on base price:\n" + o + "\n---------------------");
            }
        }
    }
}
