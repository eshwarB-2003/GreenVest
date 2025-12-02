package com.greenvest.ui;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.common.PasswordHasher;
import com.greenvest.common.SystemConfig;
import com.greenvest.controller.*;
import com.greenvest.repository.*;
import com.greenvest.repository.json.*;
import com.greenvest.service.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConsoleApp {

    public static void main(String[] args) {
        System.out.println("=== GreenVest Console ===");

        Path dataDir = Paths.get("src", "main", "resources", "data");
        JsonFileUtil fileUtil = new JsonFileUtil(dataDir);

        // Repositories
        UserRepository userRepository = new JsonUserRepository(fileUtil);
        SustainabilityActionRepository actionRepository = new JsonSustainabilityActionRepository(fileUtil);
        CarbonCreditRepository creditRepository = new JsonCarbonCreditRepository(fileUtil);
        CreditListingRepository listingRepository = new JsonCreditListingRepository(fileUtil);
        TransactionRepository transactionRepository = new JsonTransactionRepository(fileUtil);
        AuditLogRepository auditLogRepository = new JsonAuditLogRepository(fileUtil);

        // Core utilities
        SystemConfig systemConfig = new SystemConfig(fileUtil);
        PasswordHasher passwordHasher = new PasswordHasher();

        // Services
        AuthenticationService authenticationService = new AuthenticationService(userRepository, passwordHasher);
        AdminService adminService = new AdminService(actionRepository, creditRepository, listingRepository, auditLogRepository, systemConfig);
        SellerService sellerService = new SellerService(actionRepository, creditRepository, listingRepository, systemConfig);
        BuyerService buyerService = new BuyerService(transactionRepository);
        MarketplaceService marketplaceService = new MarketplaceService(listingRepository, transactionRepository);
        ReceiptService receiptService = new ReceiptService(transactionRepository);
        ReportingService reportingService = new ReportingService(transactionRepository);

        // Controllers
        AuthController authController = new AuthController(authenticationService);
        AdminController adminController = new AdminController(adminService);
        SellerController sellerController = new SellerController(sellerService);
        BuyerController buyerController = new BuyerController(buyerService);
        MarketplaceController marketplaceController = new MarketplaceController(marketplaceService);

        // Ensure default admin user
        authController.ensureDefaultAdmin();

        // UI Menus
        AdminMenu adminMenu = new AdminMenu(adminController, reportingService);
        SellerMenu sellerMenu = new SellerMenu(sellerController);
        BuyerMenu buyerMenu = new BuyerMenu(marketplaceController, buyerController, receiptService);

        MainMenu mainMenu = new MainMenu(authController, adminMenu, sellerMenu, buyerMenu);
        mainMenu.show();

        System.out.println("Exiting GreenVest. Goodbye.");
    }
}
