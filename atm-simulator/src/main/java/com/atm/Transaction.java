package com.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER }

    private final Type   type;
    private final double amount;
    private final String note;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, String note) {
        this.type      = type;
        this.amount    = amount;
        this.note      = note;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String sign = (type == Type.DEPOSIT) ? "+" : "-";
        String time = timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return String.format("  [%s]  %-12s  %s$%.2f   %s",
                time, type, sign, amount, note);
    }
}
