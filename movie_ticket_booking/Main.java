package movie_ticket_booking;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SearchService searchService = new SearchService();
        PricingStrategy pricing = new StandardPricing();
        RefundStrategy refund = new FullRefundStrategy();
        BookingService bookingService = new BookingService(pricing, refund);

        Movie m1 = new Movie("MOV1", "Inception", 148);
        Movie m2 = new Movie("MOV2", "Interstellar", 169);

        List<Seat> screen1Seats = Arrays.asList(
            new Seat("S1", "A", 1, SeatType.REGULAR),
            new Seat("S2", "A", 2, SeatType.REGULAR),
            new Seat("S3", "A", 3, SeatType.PREMIUM),
            new Seat("S4", "B", 1, SeatType.PREMIUM),
            new Seat("S5", "B", 2, SeatType.VIP)
        );

        List<Seat> screen2Seats = Arrays.asList(
            new Seat("S6", "A", 1, SeatType.REGULAR),
            new Seat("S7", "A", 2, SeatType.REGULAR),
            new Seat("S8", "B", 1, SeatType.PREMIUM)
        );

        Screen scr1 = new Screen("SCR1", screen1Seats);
        Screen scr2 = new Screen("SCR2", screen2Seats);

        Theatre t1 = new Theatre("TH1", "PVR Phoenix", "Mumbai");
        t1.addScreen(scr1);
        t1.addScreen(scr2);

        Theatre t2 = new Theatre("TH2", "INOX Nariman", "Mumbai");

        searchService.addTheatre(t1);
        searchService.addTheatre(t2);

        Show show1 = new Show("SH1", m1, scr1, "10:00 AM", t1);
        Show show2 = new Show("SH2", m2, scr2, "01:00 PM", t1);
        searchService.addShow(show1);
        searchService.addShow(show2);

        System.out.println("\n--- Theatres in Mumbai ---");
        List<Theatre> theatres = searchService.showTheatres("Mumbai");
        for (Theatre t : theatres) {
            System.out.println(t.getName() + " (" + t.getCity() + ")");
        }

        System.out.println("\n--- Movies in Mumbai ---");
        List<Movie> movies = searchService.showMovies("Mumbai");
        for (Movie m : movies) {
            System.out.println(m.getName() + " (" + m.getDurationMinutes() + " min)");
        }

        System.out.println("\n--- Shows for Inception in Mumbai ---");
        List<Show> shows = searchService.getShowsForMovie("MOV1", "Mumbai");
        for (Show s : shows) {
            System.out.println(s.getShowId() + " at " + s.getTheatre().getName() + " " + s.getStartTime());
        }

        System.out.println("\n--- Available Seats for Show SH1 ---");
        List<Seat> available = show1.getAvailableSeats();
        for (Seat s : available) {
            System.out.println(s);
        }

        System.out.println("\n--- Booking 2 seats (S1, S3) ---");
        MovieTicket ticket1 = bookingService.bookTickets(show1, Arrays.asList("S1", "S3"));

        System.out.println("\n--- Trying to book S1 again (should fail - concurrency) ---");
        bookingService.bookTickets(show1, Arrays.asList("S1"));

        System.out.println("\n--- Available Seats after booking ---");
        available = show1.getAvailableSeats();
        for (Seat s : available) {
            System.out.println(s);
        }

        System.out.println("\n--- Cancelling ticket ---");
        if (ticket1 != null) {
            bookingService.cancelTicket(ticket1);
        }

        System.out.println("\n--- Available Seats after cancellation ---");
        available = show1.getAvailableSeats();
        for (Seat s : available) {
            System.out.println(s);
        }

        System.out.println("\n--- Admin adds a new show concurrently ---");
        Show show3 = new Show("SH3", m1, scr1, "06:00 PM", t1);
        searchService.addShow(show3);

        System.out.println("\n--- Shows for Inception after addition ---");
        shows = searchService.getShowsForMovie("MOV1", "Mumbai");
        for (Show s : shows) {
            System.out.println(s.getShowId() + " at " + s.getTheatre().getName() + " " + s.getStartTime());
        }
    }
}
