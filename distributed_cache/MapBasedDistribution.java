package distributed_cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBasedDistribution implements DistributionStrategy {
    private Map<String, String> keyToNodeId;

    public MapBasedDistribution() {
        this.keyToNodeId = new HashMap<>();
    }

    public void assign(String key, String nodeId) {
        keyToNodeId.put(key, nodeId);
    }

    public CacheNode getNode(String key, List<CacheNode> nodes) {
        String targetNodeId = keyToNodeId.get(key);
        if (targetNodeId != null) {
            for (CacheNode node : nodes) {
                if (node.getNodeId().equals(targetNodeId)) {
                    return node;
                }
            }
        }
        return nodes.get(0);
    }
}
