package com.atm;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final String name;
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, String>  pins = new HashMap<>();

    public Bank(String name) { this.name = name; }

    public String getName() { return name; }

    public void addAccount(Account account, String pin) {
        accounts.put(account.getAccountNumber(), account);
        pins.put(account.getAccountNumber(), pin);
    }

    public Account authenticate(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account == null) throw new IllegalArgumentException("Account not found.");
        if (!pins.get(accountNumber).equals(pin)) throw new SecurityException("Wrong PIN.");
        return account;
    }

    public Account findAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) throw new IllegalArgumentException("Account not found: " + accountNumber);
        return account;
    }
}
