package movie_ticket_booking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        SearchService searchService = new SearchService();
        PricingEngine pricingEngine = new PricingEngine();
        RefundStrategy refundStrategy = new FullRefundStrategy();
        BookingService bookingService = new BookingService(pricingEngine, refundStrategy);
        AdminService adminService = new AdminService(searchService, pricingEngine);

        User user1 = userService.register("U1", "Dhruv", "dhruv@email.com");
        User user2 = userService.register("U2", "Arjun", "arjun@email.com");
        userService.register("U3", "Duplicate", "dhruv@email.com");

        Movie m1 = new Movie("MOV1", "Inception", 148);
        Movie m2 = new Movie("MOV2", "Interstellar", 169);
        adminService.addMovie(m1);
        adminService.addMovie(m2);

        Map<SeatType, Double> basePrices = new HashMap<>();
        basePrices.put(SeatType.BRONZE, 150.0);
        basePrices.put(SeatType.SILVER, 250.0);
        basePrices.put(SeatType.GOLD, 400.0);
        basePrices.put(SeatType.DIAMOND, 600.0);

        List<Seat> screen1Seats = Arrays.asList(
            new Seat("S1", "A", 1, SeatType.BRONZE),
            new Seat("S2", "A", 2, SeatType.BRONZE),
            new Seat("S3", "A", 3, SeatType.SILVER),
            new Seat("S4", "B", 1, SeatType.GOLD),
            new Seat("S5", "B", 2, SeatType.DIAMOND)
        );

        List<Seat> screen2Seats = Arrays.asList(
            new Seat("S6", "A", 1, SeatType.BRONZE),
            new Seat("S7", "A", 2, SeatType.SILVER),
            new Seat("S8", "B", 1, SeatType.GOLD)
        );

        Screen scr1 = new Screen("SCR1", screen1Seats, basePrices);
        Screen scr2 = new Screen("SCR2", screen2Seats, basePrices);

        Theatre t1 = new Theatre("TH1", "PVR Phoenix", "Mumbai");
        t1.addScreen(scr1);
        t1.addScreen(scr2);

        Theatre t2 = new Theatre("TH2", "INOX Nariman", "Mumbai");
        adminService.addTheatre(t1);
        adminService.addTheatre(t2);

        Show show1 = new Show("SH1", m1, scr1, "10:00 AM", t1);
        Show show2 = new Show("SH2", m2, scr2, "01:00 PM", t1);
        Show show3 = new Show("SH3", m1, scr1, "06:00 PM", t1);
        adminService.addMovieShow(show1);
        adminService.addMovieShow(show2);
        adminService.addMovieShow(show3);

        System.out.println("\n========== CUSTOMER FLOW 1: City -> Movies -> Theatres ==========");

        System.out.println("\n--- Movies in Mumbai ---");
        List<Movie> movies = searchService.showMovies("Mumbai");
        for (Movie m : movies) {
            System.out.println(m.getName() + " (" + m.getDurationMinutes() + " min)");
        }

        System.out.println("\n--- Shows for Inception in Mumbai ---");
        List<Show> shows = searchService.getShowsForMovie("MOV1", "Mumbai");
        for (Show s : shows) {
            System.out.println(s.getShowId() + " at " + s.getTheatre().getName() +
                    " Screen " + s.getScreen().getScreenId() + " " + s.getStartTime());
        }

        System.out.println("\n--- Seat Map for Show SH1 (viewable to all) ---");
        List<Seat> viewable = bookingService.viewSeats(show1);
        for (Seat s : viewable) {
            double price = pricingEngine.calculatePrice(show1, s);
            System.out.println(s + " — Rs." + price);
        }

        System.out.println("\n--- User1 books S1, S3 via UPI ---");
        PaymentProcessor upi = new UpiPayment("dhruv@upi");
        MovieTicket ticket1 = bookingService.bookTickets(show1, user1, Arrays.asList("S1", "S3"), upi);

        System.out.println("\n--- User2 tries to book S1 (already booked — concurrency) ---");
        PaymentProcessor card = new CardPayment("4111111111111234");
        bookingService.bookTickets(show1, user2, Arrays.asList("S1"), card);

        System.out.println("\n========== CUSTOMER FLOW 2: City -> Theatre -> Movies ==========");

        System.out.println("\n--- Theatres in Mumbai ---");
        List<Theatre> theatres = searchService.showTheatres("Mumbai");
        for (Theatre t : theatres) {
            System.out.println(t.getName() + " (" + t.getCity() + ")");
        }

        System.out.println("\n--- Shows at PVR Phoenix ---");
        List<Show> theatreShows = searchService.getShowsForTheatre("TH1");
        for (Show s : theatreShows) {
            System.out.println(s.getMovie().getName() + " | Screen " + s.getScreen().getScreenId() +
                    " | " + s.getStartTime());
        }

        System.out.println("\n--- User2 books S4 (Gold) via Card ---");
        bookingService.bookTickets(show1, user2, Arrays.asList("S4"), card);

        System.out.println("\n========== ADMIN: DYNAMIC PRICING RULES ==========");

        DemandPriceRule demandRule = new DemandPriceRule(50.0, 1.5);
        adminService.addPricingRule(demandRule);
        adminService.showPricingRules();

        System.out.println("\n--- Prices after demand rule (3 of 5 seats booked = 60%) ---");
        List<Seat> remaining = bookingService.viewSeats(show1);
        for (Seat s : remaining) {
            double price = pricingEngine.calculatePrice(show1, s);
            System.out.println(s + " — Rs." + price);
        }

        System.out.println("\n========== CANCELLATION WITH REFUND ==========");

        System.out.println("\n--- Cancelling User1's ticket (refund to UPI) ---");
        if (ticket1 != null) {
            bookingService.cancelTicket(ticket1);
        }

        System.out.println("\n--- Available Seats after cancellation ---");
        remaining = bookingService.viewSeats(show1);
        for (Seat s : remaining) {
            System.out.println(s);
        }

        System.out.println("\n========== SEAT LOCKING DEMO ==========");

        System.out.println("\n--- User1 selects S5 (enters payment window) ---");
        boolean locked = bookingService.selectSeats(show1, Arrays.asList("S5"));

        System.out.println("\n--- Available seats (S5 locked, not shown) ---");
        List<Seat> available = show1.getAvailableSeats();
        for (Seat s : available) {
            System.out.println(s);
        }

        System.out.println("\n--- Viewable seats (S5 still viewable but will fail on booking) ---");
        viewable = show1.getSeatsForDisplay();
        for (Seat s : viewable) {
            System.out.println(s);
        }

        System.out.println("\n--- User2 tries to lock same S5 (should fail) ---");
        bookingService.selectSeats(show1, Arrays.asList("S5"));

        System.out.println("\n--- User1 completes payment for S5 ---");
        if (locked) {
            bookingService.pay(show1, user1, Arrays.asList("S5"), upi);
        }

        System.out.println("\n========== ADMIN ADDS SHOW AT RUNTIME ==========");

        Show show4 = new Show("SH4", m2, scr1, "09:00 PM", t1);
        adminService.addMovieShow(show4);

        System.out.println("\n--- All shows at PVR Phoenix now ---");
        theatreShows = searchService.getShowsForTheatre("TH1");
        for (Show s : theatreShows) {
            System.out.println(s.getMovie().getName() + " | Screen " + s.getScreen().getScreenId() +
                    " | " + s.getStartTime());
        }
    }
}
