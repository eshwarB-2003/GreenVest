package com.greenvest.ui;

import com.greenvest.controller.AdminController;
import com.greenvest.model.AuditLogEntry;
import com.greenvest.model.CarbonCredit;
import com.greenvest.model.CreditListing;
import com.greenvest.model.SustainabilityAction;
import com.greenvest.service.ReportingService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final AdminController adminController;
    private final ReportingService reportingService;

    public AdminMenu(AdminController adminController, ReportingService reportingService) {
        this.adminController = adminController;
        this.reportingService = reportingService;
    }

    public void show(String adminId, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("=== Admin Dashboard ===");
            System.out.println("1. View pending sustainability verification requests");
            System.out.println("2. Approve / reject actions and issue credits");
            System.out.println("3. Approve listings from sellers");
            System.out.println("4. Set Base Minimum Price");
            System.out.println("5. Run expiry check");
            System.out.println("6. View audit/action history");
            System.out.println("7. View simple reports");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> viewPendingActions();
                case "2" -> reviewActions(adminId, scanner);
                case "3" -> reviewListings(adminId, scanner);
                case "4" -> setBaseMinimumPrice(adminId, scanner);
                case "5" -> runExpiryCheck(adminId);
                case "6" -> viewAuditHistory();
                case "7" -> viewReports();
                case "0" -> back = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewPendingActions() {
        List<SustainabilityAction> pending = adminController.getPendingActions();
        System.out.println();
        System.out.println("--- Pending Sustainability Actions ---");
        if (pending.isEmpty()) {
            System.out.println("No pending actions.");
            return;
        }
        for (SustainabilityAction a : pending) {
            System.out.println("ID: " + a.getId());
            System.out.println("  Seller: " + a.getSellerId());
            System.out.println("  Description: " + a.getDescription());
            System.out.println("  Proof: " + a.getProofFilePath());
            System.out.println("  Status: " + a.getStatus());
            System.out.println("-----------------------------");
        }
    }

    private void reviewActions(String adminId, Scanner scanner) {
        List<SustainabilityAction> pending = adminController.getPendingActions();
        if (pending.isEmpty()) {
            System.out.println("No pending actions to review.");
            return;
        }

        viewPendingActions();
        System.out.print("Enter action ID to review (or blank to cancel): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            return;
        }

        System.out.print("Approve (A) or Reject (R)? ");
        String mode = scanner.nextLine().trim().toUpperCase();

        if ("A".equals(mode)) {
            System.out.print("Enter number of credits to issue: ");
            int quantity;
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                return;
            }

            System.out.print("Enter expiry date for credits (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine().trim();
            LocalDate date;
            try {
                date = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
                return;
            }

            System.out.print("Optional admin note: ");
            String note = scanner.nextLine();

            try {
                adminController.approveAction(adminId, id, note, quantity, date);
                System.out.println("Action approved and credits issued.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else if ("R".equals(mode)) {
            System.out.print("Rejection reason: ");
            String note = scanner.nextLine();
            try {
                adminController.rejectAction(adminId, id, note);
                System.out.println("Action rejected.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            System.out.println("Unknown choice.");
        }
    }

    private void reviewListings(String adminId, Scanner scanner) {
        List<CreditListing> pending = adminController.getPendingListings();
        System.out.println();
        System.out.println("--- Pending Listings ---");
        if (pending.isEmpty()) {
            System.out.println("No pending listings.");
            return;
        }
        for (CreditListing l : pending) {
            System.out.println("ID: " + l.getId());
            System.out.println("  Seller: " + l.getSellerId());
            System.out.println("  CreditId: " + l.getCreditId());
            System.out.println("  Qty: " + l.getQuantity());
            System.out.println("  Price/credit: " + l.getPricePerCredit());
            System.out.println("-----------------------------");
        }

        System.out.print("Enter listing ID to review (or blank to cancel): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            return;
        }

        System.out.print("Approve (A) or Reject (R)? ");
        String mode = scanner.nextLine().trim().toUpperCase();

        if ("A".equals(mode)) {
            try {
                adminController.approveListing(adminId, id);
                System.out.println("Listing approved.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else if ("R".equals(mode)) {
            System.out.print("Rejection reason: ");
            String reason = scanner.nextLine();
            try {
                adminController.rejectListing(adminId, id, reason);
                System.out.println("Listing rejected.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            System.out.println("Unknown choice.");
        }
    }

    private void setBaseMinimumPrice(String adminId, Scanner scanner) {
        System.out.println();
        System.out.println("--- Set Base Minimum Price ---");
        System.out.println("Current base price: " + adminController.getBaseMinimumPrice());
        System.out.print("Enter new base price (or blank to cancel): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }
        try {
            double price = Double.parseDouble(input);
            adminController.setBaseMinimumPrice(adminId, price);
            System.out.println("Base minimum price updated.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void runExpiryCheck(String adminId) {
        List<CarbonCredit> expired = adminController.runExpiryCheck(adminId);
        System.out.println();
        System.out.println("--- Expiry Check ---");
        if (expired.isEmpty()) {
            System.out.println("No credits expired.");
        } else {
            System.out.println("Expired " + expired.size() + " credits:");
            for (CarbonCredit c : expired) {
                System.out.println("  Credit ID: " + c.getId()
                        + " (seller=" + c.getSellerId() + ", qty=" + c.getQuantity() + ")");
            }
        }
    }

    private void viewAuditHistory() {
        List<AuditLogEntry> entries = adminController.getAuditLog();
        System.out.println();
        System.out.println("--- Audit / Action History ---");
        if (entries.isEmpty()) {
            System.out.println("No audit entries.");
            return;
        }
        for (AuditLogEntry e : entries) {
            System.out.println("Time: " + e.getTimestamp());
            System.out.println("  Actor: " + e.getActorUserId());
            System.out.println("  Action: " + e.getActionType());
            System.out.println("  Description: " + e.getDescription());
            System.out.println("-----------------------------");
        }
    }

    private void viewReports() {
        System.out.println();
        System.out.println("--- Simple Reports ---");
        String topSeller = reportingService.getTopSeller();
        String topBuyer = reportingService.getTopBuyer();
        System.out.println("Top seller: " + topSeller);
        System.out.println("Top buyer: " + topBuyer);
    }
}

