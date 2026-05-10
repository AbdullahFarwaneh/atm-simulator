package com.atm;

import java.util.Scanner;

public class ATM {

    private final Bank bank;
    private final Scanner scanner = new Scanner(System.in);
    private Account currentAccount;

    public ATM(Bank bank) { this.bank = bank; }

    public void start() {
        printWelcome();
        while (true) {
            currentAccount = null;
            if (!login()) continue;
            session();
        }
    }


    private boolean login() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("  Enter Account Number (or 'exit'): ");
        String number = scanner.nextLine().trim();
        if (number.equalsIgnoreCase("exit")) System.exit(0);
        currentAccount = bank.findAccount(number);

        System.out.print("  Enter PIN: ");
        String pin = scanner.nextLine().trim();

        try {
            currentAccount = bank.authenticate(number, pin);
            System.out.println("\n  Welcome back, " + currentAccount.getOwnerName());
            return true;
        } catch (Exception e) {
            System.out.println("  X " + e.getMessage());
            return false;
        }
    }


    private void session() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();
            try {
                switch (choice) {
                    case "1" -> handleBalance();
                    case "2" -> handleDeposit();
                    case "3" -> handleWithdraw();
                    case "4" -> handleTransfer();
                    case "5" -> handleHistory();
                    case "6" -> handleInterest();
                    case "7" -> { System.out.println("  Logged out. Goodbye!"); running = false; }
                    default  -> System.out.println("  Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("  X " + e.getMessage());
            }
        }
    }


    private void handleBalance() {
        System.out.println("  ── Account Summary ──────────────────");
        currentAccount.printSummary();
    }

    private void handleDeposit() {
        System.out.print("  Enter deposit amount: $");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        currentAccount.deposit(amount);
    }

    private void handleWithdraw() {
        System.out.printf("  Withdrawal limit: $%.2f%n", currentAccount.getWithdrawLimit());
        System.out.print("  Enter withdrawal amount: $");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        currentAccount.withdraw(amount);
        System.out.printf("  Remaining balance: $%.2f%n", currentAccount.getBalance());
    }

    private void handleTransfer() {
        System.out.print("  Enter target account number: ");
        String targetNumber = scanner.nextLine().trim();
        Account target = bank.findAccount(targetNumber);
        System.out.print("  Enter transfer amount: $");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        currentAccount.transfer(target, amount);
    }

    private void handleHistory() {
        var history = currentAccount.getHistory();
        System.out.println("  ── Transaction History ──────────────");
        if (history.isEmpty()) { System.out.println("  No transactions yet."); return; }
        for(Transaction t : history){
            System.out.println(t);

        }
    }

    private void handleInterest() {
        if (currentAccount instanceof SavingsAccount savings) {
            savings.applyInterest();
            System.out.printf("  New balance: $%.2f%n", savings.getBalance());
        } else {
            System.out.println("  Interest is only available on Savings accounts.");
        }
    }


    private void printMenu() {
        System.out.println("\n  ── " + currentAccount.getAccountType() + " Account Menu ──────────");
        System.out.println("  [1] Check Balance");
        System.out.println("  [2] Deposit");
        System.out.println("  [3] Withdraw");
        System.out.println("  [4] Transfer");
        System.out.println("  [5] Transaction History");
        System.out.println("  [6] Apply Interest  (Savings only)");
        System.out.println("  [7] Logout");
        System.out.print("  > ");
    }

    private void printWelcome() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          " + bank.getName() + " ATM ║");
        System.out.println("║        Welcome. Please log in.       ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
}
