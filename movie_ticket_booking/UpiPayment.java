package movie_ticket_booking;

public class UpiPayment implements PaymentProcessor {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    public boolean pay(double amount) {
        System.out.println("UPI payment of Rs." + amount + " from " + upiId + " — SUCCESS");
        return true;
    }

    public boolean refund(double amount) {
        System.out.println("UPI refund of Rs." + amount + " to " + upiId + " — SUCCESS");
        return true;
    }

    public PaymentMode getMode() { return PaymentMode.UPI; }
}
