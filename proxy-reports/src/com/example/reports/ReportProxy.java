package com.example.reports;

public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl acl = new AccessControl();

    private RealReport loaded = null;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!acl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: " + user.getName()
                    + " (" + user.getRole() + ") cannot access "
                    + classification + " report [" + reportId + "]");
            return;
        }

        if (loaded == null) {
            System.out.println("[proxy] first access - loading real report...");
            loaded = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] using cached report");
        }

        loaded.display(user);
    }
}
