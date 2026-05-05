package com.atm;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank("Jordan National");

        // Pre-loaded demo accounts
        SavingsAccount  s1 = new SavingsAccount ("SAV-001", "Abdullah Farawneh", 1500.00);
        SavingsAccount  s2 = new SavingsAccount ("SAV-002", "Mohammad  ",    800.00);
        CheckingAccount c1 = new CheckingAccount("CHK-001", "zaid ",  3000.00, 500.00);
        CheckingAccount c2 = new CheckingAccount("CHK-002", "saif ",  2200.00, 300.00);

        bank.addAccount(s1, "1234");
        bank.addAccount(s2, "5678");
        bank.addAccount(c1, "4321");
        bank.addAccount(c2, "8765");

        new ATM(bank).start();
    }
}
