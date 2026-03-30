package movie_ticket_booking;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public User register(String userId, String name, String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Registration failed — email " + email + " already exists.");
                return null;
            }
        }
        User user = new User(userId, name, email);
        users.add(user);
        System.out.println("User registered: " + name + " (" + email + ")");
        return user;
    }

    public User findByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }
}
