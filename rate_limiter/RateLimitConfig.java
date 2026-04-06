package rate_limiter;

public class RateLimitConfig {
    private int maxRequests;
    private long windowSizeMs;

    public RateLimitConfig(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
    }

    public int getMaxRequests() { return maxRequests; }
    public long getWindowSizeMs() { return windowSizeMs; }

    public static RateLimitConfig perMinute(int max) {
        return new RateLimitConfig(max, 60 * 1000);
    }

    public static RateLimitConfig perHour(int max) {
        return new RateLimitConfig(max, 60 * 60 * 1000);
    }
}
