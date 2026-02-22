public abstract class Exporter {
    public final ExportResult export(ExportRequest req) {
        if (req == null) return errorResult("request must not be null");
        return doExport(req);
    }

    protected abstract ExportResult doExport(ExportRequest req);

    protected ExportResult errorResult(String message) {
        return new ExportResult("text/plain", ("ERROR: " + message).getBytes());
    }
}
