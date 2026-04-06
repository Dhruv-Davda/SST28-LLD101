package distributed_cache;

public interface EvictionPolicy {
    void keyAccessed(String key);
    String evict();
    void remove(String key);
}
