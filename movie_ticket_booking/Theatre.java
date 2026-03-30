package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String theatreId;
    private String name;
    private String city;
    private List<Screen> screens;

    public Theatre(String theatreId, String name, String city) {
        this.theatreId = theatreId;
        this.name = name;
        this.city = city;
        this.screens = new ArrayList<>();
    }

    public String getTheatreId() { return theatreId; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public List<Screen> getScreens() { return screens; }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }
}
