package service;

import model.Transaction;
import java.util.HashSet;
import java.util.Set;

public class DuplicateRule implements FraudRule {
    private Set<String> seen = new HashSet<>();

    @Override
    public boolean isFraud(Transaction<?> tx) {
        String key = tx.getAccountNumber() + "-" + tx.getAmount();
        if (seen.contains(key)) return true;
        seen.add(key);
        return false;
    }

    @Override
    public String getRuleName() {
        return "Duplicate Transaction";
    }
}