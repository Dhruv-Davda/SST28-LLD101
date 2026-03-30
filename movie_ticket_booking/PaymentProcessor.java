package movie_ticket_booking;

public interface PaymentProcessor {
    boolean pay(double amount);
    boolean refund(double amount);
    PaymentMode getMode();
}
