package service;

import model.Transaction;

public interface FraudRule {
    boolean isFraud(Transaction<?> tx);
    String getRuleName();
}