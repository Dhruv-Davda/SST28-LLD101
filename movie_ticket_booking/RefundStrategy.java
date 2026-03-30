package movie_ticket_booking;

public interface RefundStrategy {
    double calculateRefund(double totalPrice);
}
