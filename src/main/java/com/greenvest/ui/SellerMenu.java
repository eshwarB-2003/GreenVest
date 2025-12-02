package com.greenvest.ui;

import com.greenvest.controller.SellerController;
import com.greenvest.model.CarbonCredit;
import com.greenvest.model.CreditListing;
import com.greenvest.model.SustainabilityAction;

import java.util.List;
import java.util.Scanner;

public class SellerMenu {

    private final SellerController sellerController;

    public SellerMenu(SellerController sellerController) {
        this.sellerController = sellerController;
    }

    public void show(String sellerId, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("=== Seller Menu ===");
            System.out.println("1. Submit sustainability action");
            System.out.println("2. View my actions");
            System.out.println("3. View verified credits");
            System.out.println("4. List verified credits for sale");
            System.out.println("5. View my listings");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> submitAction(sellerId, scanner);
                case "2" -> viewActions(sellerId);
                case "3" -> viewVerifiedCredits(sellerId);
                case "4" -> listCreditsForSale(sellerId, scanner);
                case "5" -> viewListings(sellerId);
                case "0" -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void submitAction(String sellerId, Scanner scanner) {
        System.out.println();
        System.out.println("--- Submit Sustainability Action ---");
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Proof file path (string only): ");
        String proof = scanner.nextLine();

        SustainabilityAction action = sellerController.submitAction(sellerId, description, proof);
        System.out.println("Submitted action with ID: " + action.getId());
    }

    private void viewActions(String sellerId) {
        System.out.println();
        System.out.println("--- My Sustainability Actions ---");
        List<SustainabilityAction> actions = sellerController.getActionsForSeller(sellerId);
        if (actions.isEmpty()) {
            System.out.println("No actions yet.");
            return;
        }
        for (SustainabilityAction a : actions) {
            System.out.println("ID: " + a.getId());
            System.out.println("  Description: " + a.getDescription());
            System.out.println("  Status: " + a.getStatus());
            System.out.println("-----------------------------");
        }
    }

    private void viewVerifiedCredits(String sellerId) {
        System.out.println();
        System.out.println("--- My Verified Credits ---");
        List<CarbonCredit> credits = sellerController.getVerifiedCredits(sellerId);
        if (credits.isEmpty()) {
            System.out.println("No verified credits.");
            return;
        }
        for (CarbonCredit c : credits) {
            System.out.println("ID: " + c.getId());
            System.out.println("  Action: " + c.getSustainabilityActionId());
            System.out.println("  Quantity: " + c.getQuantity());
            System.out.println("  State: " + c.getLifecycleState());
            System.out.println("  Expiry: " + c.getExpiryDate());
            System.out.println("-----------------------------");
        }
    }

    private void listCreditsForSale(String sellerId, Scanner scanner) {
        viewVerifiedCredits(sellerId);
        System.out.print("Enter credit ID to list (or blank to cancel): ");
        String creditId = scanner.nextLine().trim();
        if (creditId.isEmpty()) {
            return;
        }

        System.out.print("Quantity to list: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }

        System.out.print("Price per credit: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        try {
            CreditListing listing = sellerController.listCredits(sellerId, creditId, quantity, price);
            System.out.println("Listing created with ID: " + listing.getId() + " (pending admin approval).");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void viewListings(String sellerId) {
        System.out.println();
        System.out.println("--- My Listings ---");
        List<CreditListing> listings = sellerController.getListingsForSeller(sellerId);
        if (listings.isEmpty()) {
            System.out.println("No listings.");
            return;
        }
        for (CreditListing l : listings) {
            System.out.println("ID: " + l.getId());
            System.out.println("  Credit: " + l.getCreditId());
            System.out.println("  Qty: " + l.getQuantity());
            System.out.println("  Price: " + l.getPricePerCredit());
            System.out.println("  Status: " + l.getStatus());
            System.out.println("-----------------------------");
        }
    }
}

