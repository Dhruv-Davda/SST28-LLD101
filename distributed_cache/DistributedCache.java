package distributed_cache;

import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private List<CacheNode> nodes;
    private DistributionStrategy distributionStrategy;
    private Database database;

    public DistributedCache(DistributionStrategy distributionStrategy, Database database) {
        this.nodes = new ArrayList<>();
        this.distributionStrategy = distributionStrategy;
        this.database = database;
    }

    public void addNode(CacheNode node) {
        nodes.add(node);
        System.out.println("Cache node added: " + node.getNodeId());
    }

    public String get(String key) {
        CacheNode node = distributionStrategy.getNode(key, nodes);
        String value = node.get(key);

        if (value == null) {
            value = database.fetch(key);
            if (value != null) {
                node.put(key, value);
                System.out.println("Loaded key=" + key + " into " + node.getNodeId() + " from DB");
            }
        }
        return value;
    }

    public void put(String key, String value) {
        CacheNode node = distributionStrategy.getNode(key, nodes);
        node.put(key, value);
        database.save(key, value);
        System.out.println("Stored key=" + key + " in " + node.getNodeId() + " and DB");
    }

    public void setDistributionStrategy(DistributionStrategy strategy) {
        this.distributionStrategy = strategy;
        System.out.println("Distribution strategy changed to: " + strategy.getClass().getSimpleName());
    }

    public void showCacheStatus() {
        System.out.println("\n--- Cache Status ---");
        for (CacheNode node : nodes) {
            node.showContents();
        }
        System.out.println("--------------------\n");
    }
}
