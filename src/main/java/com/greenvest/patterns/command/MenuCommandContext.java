package com.greenvest.patterns.command;

import java.util.Scanner;

public class MenuCommandContext {

    private final Scanner scanner;

    public MenuCommandContext(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
