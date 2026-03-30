package movie_ticket_booking;

import java.util.List;
import java.util.UUID;

public class MovieTicket {
    private String ticketId;
    private Show show;
    private List<Seat> seats;
    private double totalPrice;
    private BookingStatus status;

    public MovieTicket(Show show, List<Seat> seats, double totalPrice) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8);
        this.show = show;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.status = BookingStatus.CONFIRMED;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
