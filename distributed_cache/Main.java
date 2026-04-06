package distributed_cache;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.save("user:1", "Dhruv");
        db.save("user:2", "Arjun");
        db.save("user:3", "Meera");
        db.save("user:4", "Riya");
        db.save("user:5", "Karan");

        System.out.println("========== MODULO DISTRIBUTION + LRU EVICTION ==========\n");

        DistributionStrategy modulo = new ModuloDistribution();
        DistributedCache cache = new DistributedCache(modulo, db);

        cache.addNode(new CacheNode("Node-A", 2, new LRUEvictionPolicy()));
        cache.addNode(new CacheNode("Node-B", 2, new LRUEvictionPolicy()));
        cache.addNode(new CacheNode("Node-C", 2, new LRUEvictionPolicy()));

        System.out.println("\n--- put() operations ---");
        cache.put("color", "red");
        cache.put("lang", "java");
        cache.put("fruit", "apple");

        cache.showCacheStatus();

        System.out.println("--- get() with cache hit ---");
        String val = cache.get("color");
        System.out.println("Got: " + val);

        System.out.println("\n--- get() with cache miss (fetch from DB) ---");
        val = cache.get("user:1");
        System.out.println("Got: " + val);

        val = cache.get("user:2");
        System.out.println("Got: " + val);

        cache.showCacheStatus();

        System.out.println("--- get() key not in DB either ---");
        val = cache.get("user:99");
        System.out.println("Got: " + val);

        System.out.println("\n--- Eviction Demo (Node capacity = 2) ---");
        cache.put("animal", "dog");
        cache.put("city", "mumbai");
        cache.put("country", "india");
        cache.put("planet", "earth");

        cache.showCacheStatus();

        System.out.println("========== SWITCHING TO MAP-BASED DISTRIBUTION ==========\n");

        MapBasedDistribution mapDist = new MapBasedDistribution();
        mapDist.assign("vip:1", "Node-A");
        mapDist.assign("vip:2", "Node-B");
        mapDist.assign("vip:3", "Node-C");

        cache.setDistributionStrategy(mapDist);

        cache.put("vip:1", "Gold User");
        cache.put("vip:2", "Silver User");
        cache.put("vip:3", "Bronze User");

        cache.showCacheStatus();

        System.out.println("--- get() with map-based routing ---");
        val = cache.get("vip:1");
        System.out.println("Got: " + val);

        System.out.println("\n========== MRU EVICTION DEMO ==========\n");

        Database db2 = new Database();
        DistributedCache mruCache = new DistributedCache(new ModuloDistribution(), db2);
        mruCache.addNode(new CacheNode("MRU-Node", 3, new MRUEvictionPolicy()));

        mruCache.put("a", "1");
        mruCache.put("b", "2");
        mruCache.put("c", "3");

        System.out.println("\n--- Access 'a' then 'c', then insert 'd' ---");
        mruCache.get("a");
        mruCache.get("c");
        mruCache.put("d", "4");

        System.out.println("\n--- MRU evicts most recently used ('c') ---");
        mruCache.showCacheStatus();
    }
}
