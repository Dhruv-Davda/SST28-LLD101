package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private PricingStrategy pricingStrategy;
    private RefundStrategy refundStrategy;
    private List<MovieTicket> allBookings;

    public BookingService(PricingStrategy pricingStrategy, RefundStrategy refundStrategy) {
        this.pricingStrategy = pricingStrategy;
        this.refundStrategy = refundStrategy;
        this.allBookings = new ArrayList<>();
    }

    public MovieTicket bookTickets(Show show, List<String> seatIds) {
        List<Seat> availableSeats = show.getAvailableSeats();
        List<Seat> requestedSeats = new ArrayList<>();

        for (String seatId : seatIds) {
            boolean found = false;
            for (Seat s : availableSeats) {
                if (s.getSeatId().equals(seatId)) {
                    requestedSeats.add(s);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Seat " + seatId + " is not available.");
                return null;
            }
        }

        boolean locked = show.bookSeats(seatIds);
        if (!locked) {
            System.out.println("Booking failed — seats already taken (concurrent booking).");
            return null;
        }

        double totalPrice = 0;
        for (Seat s : requestedSeats) {
            totalPrice += pricingStrategy.getPrice(s.getType());
        }

        MovieTicket ticket = new MovieTicket(show, requestedSeats, totalPrice);
        allBookings.add(ticket);
        System.out.println("Booking confirmed! Ticket: " + ticket.getTicketId() +
                " | Seats: " + requestedSeats + " | Total: Rs." + totalPrice);
        return ticket;
    }

    public double cancelTicket(MovieTicket ticket) {
        if (ticket.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("Ticket " + ticket.getTicketId() + " is already cancelled.");
            return 0;
        }

        ticket.cancel();

        List<String> seatIds = new ArrayList<>();
        for (Seat s : ticket.getSeats()) {
            seatIds.add(s.getSeatId());
        }
        ticket.getShow().releaseSeats(seatIds);

        double refund = refundStrategy.calculateRefund(ticket.getTotalPrice());
        System.out.println("Ticket " + ticket.getTicketId() + " cancelled. Refund: Rs." + refund);
        return refund;
    }
}
