package movie_ticket_booking;

import java.util.List;
import java.util.UUID;

public class MovieTicket {
    private String ticketId;
    private Show show;
    private User user;
    private List<Seat> seats;
    private double totalPrice;
    private BookingStatus status;
    private PaymentProcessor paymentProcessor;

    public MovieTicket(Show show, User user, List<Seat> seats, double totalPrice, PaymentProcessor paymentProcessor) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8);
        this.show = show;
        this.user = user;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.status = BookingStatus.CONFIRMED;
        this.paymentProcessor = paymentProcessor;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public User getUser() { return user; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }
    public PaymentProcessor getPaymentProcessor() { return paymentProcessor; }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
