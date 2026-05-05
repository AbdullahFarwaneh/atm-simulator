package com.atm;

public class SavingsAccount extends Account {

    private static final double WITHDRAW_LIMIT     = 1000.0;
    private static final double MIN_BALANCE        = 100.0;
    private static final double INTEREST_RATE      = 0.03;   // 3% annual

    public SavingsAccount(String accountNumber, String ownerName, double initialBalance) {
        super(accountNumber, ownerName, initialBalance);
    }

    @Override public String getAccountType()  { return "Savings"; }
    @Override public double getWithdrawLimit(){ return WITHDRAW_LIMIT; }

    @Override
    protected boolean canWithdraw(double amount) {
        return (balance - amount) >= MIN_BALANCE;
    }

    /** Apply monthly interest to the balance */
    public void applyInterest() {
        double interest = balance * (INTEREST_RATE / 12);
        balance += interest;
        getHistory().add(new Transaction(Transaction.Type.DEPOSIT, interest, "Monthly Interest"));
        System.out.printf("    Interest applied: +$%.2f%n", interest);
    }

    @Override
    public void printSummary() {
        super.printSummary();
        System.out.printf("  Min Balance : $%.2f%n", MIN_BALANCE);
        System.out.printf("  Interest    : %.0f%% annual%n", INTEREST_RATE * 100);
    }
}
