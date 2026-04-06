package rate_limiter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowCounter implements RateLimiter {
    private RateLimitConfig config;
    private Map<String, Queue<Long>> requestLog;

    public SlidingWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.requestLog = new HashMap<>();
    }

    public synchronized boolean isAllowed(String key) {
        long now = System.currentTimeMillis();
        long windowStart = now - config.getWindowSizeMs();

        if (!requestLog.containsKey(key)) {
            requestLog.put(key, new LinkedList<>());
        }

        Queue<Long> timestamps = requestLog.get(key);

        while (!timestamps.isEmpty() && timestamps.peek() <= windowStart) {
            timestamps.poll();
        }

        if (timestamps.size() < config.getMaxRequests()) {
            timestamps.add(now);
            return true;
        }

        return false;
    }
}
