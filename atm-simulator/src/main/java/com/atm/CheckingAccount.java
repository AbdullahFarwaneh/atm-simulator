package com.atm;

public class CheckingAccount extends Account {

    private static final double WITHDRAW_LIMIT = 5000.0;
    private final double overdraftLimit;

    public CheckingAccount(String accountNumber, String ownerName,
                           double initialBalance, double overdraftLimit) {
        super(accountNumber, ownerName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override public String getAccountType()  { return "Checking"; }
    @Override public double getWithdrawLimit(){ return WITHDRAW_LIMIT; }

    @Override
    protected boolean canWithdraw(double amount) {
        return (balance - amount) >= -overdraftLimit;
    }

    @Override
    public void printSummary() {
        super.printSummary();
        System.out.printf("  Overdraft   : up to $%.2f%n", overdraftLimit);
    }
}
