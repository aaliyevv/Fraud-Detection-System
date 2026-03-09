package service;

import model.Transaction;

public class AmountRule implements FraudRule {
    @Override
    public boolean isFraud(Transaction<?> tx) {
        return tx.getAmount() > 10000;
    }

    @Override
    public String getRuleName() {
        return "Amount > 10000";
    }
}