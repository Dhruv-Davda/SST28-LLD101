package pen;

public class ClickMechanism implements OpenCloseStrategy {
    public void open() {
        System.out.println("Clicking the top button to extend the nib.");
    }
    public void close() {
        System.out.println("Clicking the top button to retract the nib.");
    }
}
