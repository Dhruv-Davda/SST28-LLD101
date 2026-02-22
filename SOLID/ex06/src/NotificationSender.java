public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    public final String send(Notification n) {
        if (n == null) return "ERROR: notification must not be null";
        return doSend(n);
    }

    protected abstract String doSend(Notification n);
}
