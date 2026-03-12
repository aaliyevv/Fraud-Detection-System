package service;

import model.Transaction;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RapidTransactionsRule implements FraudRule {

    private final Map<String, List<Transaction<?>>> accountTransactions = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    // This method returns to FraudDetector the fraud transactions from the last 5 minutes.
    public List<Transaction<?>> getFraudTransactions(Transaction<?> tx) {
        String acc = tx.getAccountNumber();
        LocalTime currentTime = LocalTime.parse(tx.getTime(), formatter);

        accountTransactions.putIfAbsent(acc, new ArrayList<>());
        List<Transaction<?>> transactions = accountTransactions.get(acc);

        // Find transactions in the last 5 minutes
        List<Transaction<?>> recent = new ArrayList<>();
        for (Transaction<?> t : transactions) {
            LocalTime tTime = LocalTime.parse(t.getTime(), formatter);
            long diffSec = Math.abs(currentTime.toSecondOfDay() - tTime.toSecondOfDay());
            if (diffSec <= 300) { // 5 minutes = 300 seconds
                recent.add(t);
            }
        }

        // Add new operation also
        recent.add(tx);
        transactions.add(tx);

        if (recent.size() >= 3) {
            return recent; // all transactions in last 5 minutes is fraud
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isFraud(Transaction<?> tx) {
        // returns whether the current transaction is fraudulent or not.
        return getFraudTransactions(tx).size() > 0;
    }

    @Override
    public String getRuleName() {

        return "Rapid Transactions";
    }
}