package movie_ticket_booking;

public class FullRefundStrategy implements RefundStrategy {
    public double calculateRefund(double totalPrice) {
        return totalPrice;
    }
}
