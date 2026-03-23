package pen;

public class CapMechanism implements OpenCloseStrategy {
    public void open() {
        System.out.println("Removing the cap.");
    }
    public void close() {
        System.out.println("Putting the cap back on.");
    }
}
