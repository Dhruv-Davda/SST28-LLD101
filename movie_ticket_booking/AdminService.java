package movie_ticket_booking;

import java.util.List;

public class AdminService {
    private SearchService searchService;
    private PricingEngine pricingEngine;

    public AdminService(SearchService searchService, PricingEngine pricingEngine) {
        this.searchService = searchService;
        this.pricingEngine = pricingEngine;
    }

    public void addMovie(Movie movie) {
        searchService.addMovie(movie);
        System.out.println("Admin added movie: " + movie.getName());
    }

    public void addTheatre(Theatre theatre) {
        searchService.addTheatre(theatre);
        System.out.println("Admin added theatre: " + theatre.getName() + " in " + theatre.getCity());
    }

    public synchronized void addMovieShow(Show show) {
        searchService.addShow(show);
        System.out.println("Admin added show: " + show.getMovie().getName() +
                " at " + show.getTheatre().getName() + " Screen " + show.getScreen().getScreenId() +
                " (" + show.getStartTime() + ")");
    }

    public void addPricingRule(PricingRule rule) {
        pricingEngine.addRule(rule);
        System.out.println("Admin activated pricing rule: " + rule.getRuleName());
    }

    public void removePricingRule(PricingRule rule) {
        pricingEngine.removeRule(rule);
        System.out.println("Admin deactivated pricing rule: " + rule.getRuleName());
    }

    public void showPricingRules() {
        pricingEngine.showActiveRules();
    }
}
