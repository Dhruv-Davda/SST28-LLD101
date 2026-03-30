package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private List<Theatre> allTheatres;
    private List<Show> allShows;
    private List<Movie> allMovies;

    public SearchService() {
        this.allTheatres = new ArrayList<>();
        this.allShows = new ArrayList<>();
        this.allMovies = new ArrayList<>();
    }

    public void addTheatre(Theatre theatre) {
        allTheatres.add(theatre);
    }

    public synchronized void addMovie(Movie movie) {
        allMovies.add(movie);
    }

    public synchronized void addShow(Show show) {
        allShows.add(show);
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

    public List<Show> getShowsForTheatre(String theatreId) {
        List<Show> result = new ArrayList<>();
        for (Show show : allShows) {
            if (show.getTheatre().getTheatreId().equals(theatreId)) {
                result.add(show);
            }
        }
        return result;
    }
}
