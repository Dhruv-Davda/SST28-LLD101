package movie_ticket_booking;

public class Movie {
    private String movieId;
    private String name;
    private int durationMinutes;

    public Movie(String movieId, String name, int durationMinutes) {
        this.movieId = movieId;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public String getMovieId() { return movieId; }
    public String getName() { return name; }
    public int getDurationMinutes() { return durationMinutes; }
}
