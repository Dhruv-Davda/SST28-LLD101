package rate_limiter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExternalResource paidApi = new ExternalResource("ChatGPT-API");

        System.out.println("========== FIXED WINDOW COUNTER ==========");
        System.out.println("Config: 3 requests per minute per tenant\n");

        RateLimitConfig config = RateLimitConfig.perMinute(3);
        RateLimiter fixedWindow = new FixedWindowCounter(config);
        InternalService service = new InternalService(fixedWindow, paidApi);

        service.handleRequest("tenant:T1", "translate hello", true);
        service.handleRequest("tenant:T1", "translate world", true);
        service.handleRequest("tenant:T1", "translate foo", true);
        service.handleRequest("tenant:T1", "translate bar", true);
        service.handleRequest("tenant:T1", "translate baz", true);

        System.out.println("\n--- Different tenant (T2) has its own limit ---");
        service.handleRequest("tenant:T2", "summarize doc", true);
        service.handleRequest("tenant:T2", "summarize report", true);

        System.out.println("\n--- Request with no external call (no rate limit check) ---");
        service.handleRequest("tenant:T1", "get cached data", false);

        System.out.println("\n\n========== SWITCHING TO SLIDING WINDOW COUNTER ==========");
        System.out.println("Config: 5 requests per 2 seconds per customer\n");

        RateLimitConfig slidingConfig = new RateLimitConfig(5, 2000);
        RateLimiter slidingWindow = new SlidingWindowCounter(slidingConfig);
        service.setRateLimiter(slidingWindow);

        for (int i = 1; i <= 7; i++) {
            String result = service.handleRequest("customer:C1", "request-" + i, true);
            System.out.println("Result: " + result);
        }

        System.out.println("\n--- Waiting 2 seconds for window to slide ---");
        Thread.sleep(2100);

        System.out.println("\n--- After window slides, quota resets ---");
        service.handleRequest("customer:C1", "request-after-wait", true);

        System.out.println("\n\n========== RATE LIMIT BY API KEY ==========\n");

        RateLimitConfig strictConfig = RateLimitConfig.perMinute(2);
        RateLimiter strictLimiter = new FixedWindowCounter(strictConfig);
        InternalService strictService = new InternalService(strictLimiter, paidApi);

        strictService.handleRequest("apikey:ABC123", "analyze image", true);
        strictService.handleRequest("apikey:ABC123", "analyze video", true);
        strictService.handleRequest("apikey:ABC123", "analyze audio", true);

        System.out.println("\n--- Different API key has separate limit ---");
        strictService.handleRequest("apikey:XYZ789", "analyze text", true);
    }
}
