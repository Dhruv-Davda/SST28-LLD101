package com.example.reports;

public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        Report pubReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer rv = new ReportViewer();

        System.out.println("=== CampusVault Demo ===\n");

        rv.open(pubReport, student);
        System.out.println();

        rv.open(facultyReport, student);
        System.out.println();

        rv.open(facultyReport, faculty);
        System.out.println();

        rv.open(adminReport, admin);
        System.out.println();

        rv.open(adminReport, admin);
    }
}
