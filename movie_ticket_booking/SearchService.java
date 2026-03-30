package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private List<Theatre> allTheatres;
    private List<Show> allShows;

    public SearchService() {
        this.allTheatres = new ArrayList<>();
        this.allShows = new ArrayList<>();
    }

    public void addTheatre(Theatre theatre) {
        allTheatres.add(theatre);
    }

    public synchronized void addShow(Show show) {
        allShows.add(show);
        System.out.println("Show added: " + show.getShowId() + " | " + show.getMovie().getName() +
                " at " + show.getTheatre().getName() + " (" + show.getStartTime() + ")");
    }

    public List<Theatre> showTheatres(String city) {
        List<Theatre> result = new ArrayList<>();
        for (Theatre t : allTheatres) {
            if (t.getCity().equalsIgnoreCase(city)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Movie> showMovies(String city) {
        List<Movie> result = new ArrayList<>();
        List<String> seen = new ArrayList<>();

        for (Show show : allShows) {
            if (show.getTheatre().getCity().equalsIgnoreCase(city)) {
                if (!seen.contains(show.getMovie().getMovieId())) {
                    result.add(show.getMovie());
                    seen.add(show.getMovie().getMovieId());
                }
            }
        }
        return result;
    }

    public List<Show> getShowsForMovie(String movieId, String city) {
        List<Show> result = new ArrayList<>();
        for (Show show : allShows) {
            if (show.getMovie().getMovieId().equals(movieId) &&
                    show.getTheatre().getCity().equalsIgnoreCase(city)) {
                result.add(show);
            }
        }
        return result;
    }
}
