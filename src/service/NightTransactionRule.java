package service;

import model.Transaction;

public class NightTransactionRule implements FraudRule {
    @Override
    public boolean isFraud(Transaction<?> tx) {
        String[] parts = tx.getTime().split(":");
        int hour = Integer.parseInt(parts[0]);
        return hour >= 0 && hour < 6;
    }

    @Override
    public String getRuleName() {

        return "Night Transaction";
    }
}