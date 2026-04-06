package rate_limiter;

public class InternalService {
    private RateLimiter rateLimiter;
    private ExternalResource externalResource;

    public InternalService(RateLimiter rateLimiter, ExternalResource externalResource) {
        this.rateLimiter = rateLimiter;
        this.externalResource = externalResource;
    }

    public void setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
        System.out.println("Rate limiter switched to: " + rateLimiter.getClass().getSimpleName());
    }

    public String handleRequest(String rateLimitKey, String requestData, boolean needsExternalCall) {
        System.out.println("\n--- Request from [" + rateLimitKey + "]: " + requestData + " ---");

        String businessResult = runBusinessLogic(requestData);

        if (!needsExternalCall) {
            System.out.println("No external call needed. Returning business result.");
            return businessResult;
        }

        if (!rateLimiter.isAllowed(rateLimitKey)) {
            System.out.println("RATE LIMITED! External call denied for key: " + rateLimitKey);
            return "Rate limit exceeded. Try again later.";
        }

        String externalResult = externalResource.call(requestData);
        return businessResult + " | " + externalResult;
    }

    private String runBusinessLogic(String data) {
        return "Processed: " + data;
    }
}
