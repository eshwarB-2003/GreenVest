package com.greenvest.ui;

import com.greenvest.controller.BuyerController;
import com.greenvest.controller.MarketplaceController;
import com.greenvest.model.CreditListing;
import com.greenvest.model.Receipt;
import com.greenvest.model.Transaction;
import com.greenvest.service.ReceiptService;

import java.util.List;
import java.util.Scanner;

public class BuyerMenu {

    private final MarketplaceController marketplaceController;
    private final BuyerController buyerController;
    private final ReceiptService receiptService;

    public BuyerMenu(MarketplaceController marketplaceController,
                     BuyerController buyerController,
                     ReceiptService receiptService) {
        this.marketplaceController = marketplaceController;
        this.buyerController = buyerController;
        this.receiptService = receiptService;
    }

    public void show(String buyerId, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("=== Buyer Menu ===");
            System.out.println("1. Marketplace (view and buy credits)");
            System.out.println("2. View transactions / receipts");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> marketplace(buyerId, scanner);
                case "2" -> viewTransactions(buyerId);
                case "0" -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void marketplace(String buyerId, Scanner scanner) {
        boolean back = false;

        while (!back) {
            List<CreditListing> listings = marketplaceController.getMarketplaceListings();
            System.out.println();
            System.out.println("--- Marketplace ---");
            if (listings.isEmpty()) {
                System.out.println("No active listings.");
                return;
            }
            for (int i = 0; i < listings.size(); i++) {
                CreditListing l = listings.get(i);
                System.out.println((i + 1) + ". ID: " + l.getId()
                        + " | Seller: " + l.getSellerId()
                        + " | Qty: " + l.getQuantity()
                        + " | Price: " + l.getPricePerCredit());
            }
            System.out.println("0. Back");
            System.out.print("Select listing number: ");

            String input = scanner.nextLine().trim();
            if ("0".equals(input)) {
                back = true;
                continue;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (index < 0 || index >= listings.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            CreditListing selected = listings.get(index);
            System.out.print("Quantity to purchase: ");
            int quantity;
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                continue;
            }

            try {
                Transaction tx = marketplaceController.purchase(buyerId, selected.getId(), quantity);
                Receipt receipt = receiptService.generateReceipt(tx);
                System.out.println();
                System.out.println("Purchase successful. Receipt:");
                System.out.println(receipt.getRawText());
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void viewTransactions(String buyerId) {
        System.out.println();
        System.out.println("--- My Transactions ---");
        List<Transaction> transactions = buyerController.getTransactionsForBuyer(buyerId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println("Transaction ID: " + t.getId());
            System.out.println("  Listing: " + t.getListingId());
            System.out.println("  Qty: " + t.getQuantity());
            System.out.println("  Total: " + t.getTotalAmount());
            System.out.println("  Completed: " + t.getCompletedAt());
            System.out.println("-----------------------------");
        }
    }
}

