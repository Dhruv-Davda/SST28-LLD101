package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private PricingEngine pricingEngine;
    private RefundStrategy refundStrategy;
    private List<MovieTicket> allBookings;

    public BookingService(PricingEngine pricingEngine, RefundStrategy refundStrategy) {
        this.pricingEngine = pricingEngine;
        this.refundStrategy = refundStrategy;
        this.allBookings = new ArrayList<>();
    }

    public List<Seat> viewSeats(Show show) {
        return show.getSeatsForDisplay();
    }

    public boolean selectSeats(Show show, List<String> seatIds) {
        boolean locked = show.lockSeats(seatIds);
        if (!locked) {
            System.out.println("Some seats are not available (already locked or booked).");
            return false;
        }
        System.out.println("Seats locked for payment: " + seatIds);
        return true;
    }

    public MovieTicket pay(Show show, User user, List<String> seatIds, PaymentProcessor processor) {
        List<Seat> selectedSeats = new ArrayList<>();
        for (Seat s : show.getScreen().getSeats()) {
            if (seatIds.contains(s.getSeatId())) {
                selectedSeats.add(s);
            }
        }

        double totalPrice = 0;
        for (Seat s : selectedSeats) {
            totalPrice += pricingEngine.calculatePrice(show, s);
        }

        boolean paid = processor.pay(totalPrice);
        if (!paid) {
            System.out.println("Payment failed. Releasing seats.");
            show.releaseLock(seatIds);
            return null;
        }

        boolean confirmed = show.confirmBooking(seatIds);
        if (!confirmed) {
            System.out.println("Booking failed — lock expired. Refunding.");
            processor.refund(totalPrice);
            return null;
        }

        MovieTicket ticket = new MovieTicket(show, user, selectedSeats, totalPrice, processor);
        allBookings.add(ticket);
        System.out.println("Booking confirmed! Ticket: " + ticket.getTicketId() +
                " | Movie: " + show.getMovie().getName() +
                " | Seats: " + selectedSeats + " | Total: Rs." + totalPrice);
        return ticket;
    }

    public MovieTicket bookTickets(Show show, User user, List<String> seatIds, PaymentProcessor processor) {
        boolean locked = selectSeats(show, seatIds);
        if (!locked) return null;
        return pay(show, user, seatIds, processor);
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
        ticket.getPaymentProcessor().refund(refund);
        System.out.println("Ticket " + ticket.getTicketId() + " cancelled. Refund: Rs." + refund +
                " via " + ticket.getPaymentProcessor().getMode());
        return refund;
    }
}
