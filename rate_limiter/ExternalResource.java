package rate_limiter;

public class ExternalResource {
    private String name;

    public ExternalResource(String name) {
        this.name = name;
    }

    public String call(String data) {
        System.out.println("[" + name + "] External API called with: " + data);
        return "Response for: " + data;
    }

    public String getName() { return name; }
}
