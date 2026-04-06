package distributed_cache;

import java.util.HashMap;
import java.util.Map;

public class CacheNode {
    private String nodeId;
    private int capacity;
    private Map<String, String> store;
    private EvictionPolicy evictionPolicy;

    public CacheNode(String nodeId, int capacity, EvictionPolicy evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.store = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String getNodeId() { return nodeId; }

    public String get(String key) {
        if (store.containsKey(key)) {
            evictionPolicy.keyAccessed(key);
            System.out.println("[" + nodeId + "] Cache HIT: key=" + key);
            return store.get(key);
        }
        System.out.println("[" + nodeId + "] Cache MISS: key=" + key);
        return null;
    }

    public void put(String key, String value) {
        if (store.containsKey(key)) {
            store.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }

        if (store.size() >= capacity) {
            String evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                store.remove(evictedKey);
                System.out.println("[" + nodeId + "] Evicted key=" + evictedKey);
            }
        }

        store.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    public void showContents() {
        System.out.println("[" + nodeId + "] " + store);
    }
}
