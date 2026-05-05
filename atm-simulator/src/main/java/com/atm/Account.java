package com.atm;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private final String accountNumber;
    private final String ownerName;
    protected double balance;
    private final List<Transaction> history = new ArrayList<>();

    public Account(String accountNumber,String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public double getBalance()       { return balance; }
    public List<Transaction> getHistory() { return history; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        history.add(new Transaction(Transaction.Type.DEPOSIT, amount, "Deposit"));
        System.out.printf("   Deposited $%.2f successfully.%n", amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (amount > getWithdrawLimit()) throw new IllegalStateException(
                "Exceeds withdrawal limit of $" + getWithdrawLimit());
        if (!canWithdraw(amount)) throw new IllegalStateException("Insufficient funds.");
        balance -= amount;
        history.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Withdrawal"));
        System.out.printf("    Withdrew $%.2f successfully.%n", amount);
    }

    public void transfer(Account target, double amount) {
        this.withdraw(amount);
        history.add(new Transaction(Transaction.Type.TRANSFER, amount, "→ " + target.getAccountNumber()));
        target.balance += amount;
        target.getHistory().add(new Transaction(Transaction.Type.TRANSFER, amount, "← " + this.accountNumber));
        System.out.printf("    Transferred $%.2f to %s.%n", amount, target.getAccountNumber());
    }

    /** Subclasses define their own type label */
    public abstract String getAccountType();

    /** Subclasses define withdrawal rules */
    protected abstract boolean canWithdraw(double amount);

    /** Subclasses define their withdrawal limit */
    public abstract double getWithdrawLimit();

    public void printSummary() {
        System.out.println("  Account  : " + accountNumber);
        System.out.println("  Owner    : " + ownerName);
        System.out.println("  Type     : " + getAccountType());
        System.out.printf ("  Balance  : $%.2f%n", balance);
    }
}
