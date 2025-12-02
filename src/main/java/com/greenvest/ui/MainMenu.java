package com.greenvest.ui;

import com.greenvest.controller.AuthController;
import com.greenvest.model.User;
import com.greenvest.model.UserRole;

import java.util.Optional;
import java.util.Scanner;

public class MainMenu {

    private final AuthController authController;
    private final AdminMenu adminMenu;
    private final SellerMenu sellerMenu;
    private final BuyerMenu buyerMenu;

    public MainMenu(AuthController authController,
                    AdminMenu adminMenu,
                    SellerMenu sellerMenu,
                    BuyerMenu buyerMenu) {
        this.authController = authController;
        this.adminMenu = adminMenu;
        this.sellerMenu = sellerMenu;
        this.buyerMenu = buyerMenu;
    }

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println();
                System.out.println("=== GreenVest Main Menu ===");
                System.out.println("1. Login");
                System.out.println("2. Register as Seller");
                System.out.println("3. Register as Buyer");
                System.out.println("0. Exit");
                System.out.print("Select an option: ");

                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1" -> login(scanner);
                    case "2" -> registerSeller(scanner);
                    case "3" -> registerBuyer(scanner);
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println();
        System.out.println("--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<User> userOpt = authController.handleLogin(username, password);
        if (userOpt.isEmpty()) {
            System.out.println("Login failed. Invalid username or password.");
            return;
        }

        User user = userOpt.get();
        System.out.println("Login successful as " + user.getRole() + ".");

        if (user.getRole() == UserRole.ADMIN) {
            adminMenu.show(user.getId(), scanner);
        } else if (user.getRole() == UserRole.SELLER) {
            sellerMenu.show(user.getId(), scanner);
        } else if (user.getRole() == UserRole.BUYER) {
            buyerMenu.show(user.getId(), scanner);
        } else {
            System.out.println("Unknown role. Returning to main menu.");
        }
    }

    private void registerSeller(Scanner scanner) {
        System.out.println();
        System.out.println("--- Register as Seller ---");
        System.out.print("Choose username: ");
        String username = scanner.nextLine();
        System.out.print("Choose password: ");
        String password = scanner.nextLine();
        System.out.print("Email (optional, press Enter to skip): ");
        String email = scanner.nextLine();
        System.out.print("Organisation name: ");
        String orgName = scanner.nextLine();

        try {
            authController.handleSellerRegistration(username, password,
                    email.isBlank() ? null : email,
                    orgName);
            System.out.println("Seller registered successfully. You can now log in.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Registration failed: " + ex.getMessage());
        }
    }

    private void registerBuyer(Scanner scanner) {
        System.out.println();
        System.out.println("--- Register as Buyer ---");
        System.out.print("Choose username: ");
        String username = scanner.nextLine();
        System.out.print("Choose password: ");
        String password = scanner.nextLine();
        System.out.print("Email (optional, press Enter to skip): ");
        String email = scanner.nextLine();
        System.out.print("Company name: ");
        String companyName = scanner.nextLine();

        try {
            authController.handleBuyerRegistration(username, password,
                    email.isBlank() ? null : email,
                    companyName);
            System.out.println("Buyer registered successfully. You can now log in.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Registration failed: " + ex.getMessage());
        }
    }
}
