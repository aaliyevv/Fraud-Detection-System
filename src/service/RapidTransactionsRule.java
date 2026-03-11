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

        