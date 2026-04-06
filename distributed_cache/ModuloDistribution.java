package distributed_cache;

import java.util.List;

public class ModuloDistribution implements DistributionStrategy {

    public CacheNode getNode(String key, List<CacheNode> nodes) {
        int hash = Math.abs(key.hashCode());
        int index = hash % nodes.size();
        return nodes.get(index);
    }
}
