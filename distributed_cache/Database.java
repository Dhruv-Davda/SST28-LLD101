package distributed_cache;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> store;

    public Database() {
        this.store = new HashMap<>();
    }

    public void save(String key, String value) {
        store.put(key, value);
    }

    public String fetch(String key) {
        if (store.containsKey(key)) {
            System.out.println("[DB] Fetched key=" + key + " from database");
            return store.get(key);
        }
        System.out.println("[DB] Key=" + key + " not found in database either");
        return null;
    }

    public void showAll() {
        System.out.println("[DB] Contents: " + store);
    }
}
