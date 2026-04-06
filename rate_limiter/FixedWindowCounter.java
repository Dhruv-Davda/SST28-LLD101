package rate_limiter;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowCounter implements RateLimiter {
    private RateLimitConfig config;
    private Map<String, long[]> windowMap;

    public FixedWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.windowMap = new HashMap<>();
    }

    public synchronized boolean isAllowed(String key) {
        long now = System.currentTimeMillis();
        long windowStart = now - (now % config.getWindowSizeMs());

        if (!windowMap.containsKey(key)) {
            windowMap.put(key, new long[]{windowStart, 1});
            return true;
        }

        long[] data = windowMap.get(key);
        long storedWindowStart = data[0];
        long count = data[1];

        if (storedWindowStart != windowStart) {
            windowMap.put(key, new long[]{windowStart, 1});
            return true;
        }

        if (count < config.getMaxRequests()) {
            data[1] = count + 1;
            return true;
        }

        return false;
    }
}
