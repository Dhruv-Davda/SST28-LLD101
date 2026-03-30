package movie_ticket_booking;

public class PartialRefundStrategy implements RefundStrategy {
    private double refundPercent;

    public PartialRefundStrategy(double refundPercent) {
        this.refundPercent = refundPercent;
    }

    public double calculateRefund(double totalPrice) {
        return totalPrice * (refundPercent / 100.0);
    }
}
