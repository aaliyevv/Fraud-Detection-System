package service;

import model.Transaction;
import java.util.*;

public class FraudDetector {
    private List<FraudRule> rules = new ArrayList<>();
    private Map<Transaction<?>, String> frauds = new LinkedHashMap<>();

    public void addRule(FraudRule rule) {

        rules.add(rule);
    }

    public <T> void detect(List<Transaction<T>> transactions) {
        for (Transaction<T> tx : transactions) {
            for (FraudRule rule : rules) {

                if (rule.isFraud(tx)) {

                    if (rule instanceof RapidTransactionsRule) {
                        // Add the whole operations during last 5 minutes to fruad map for rapidTransactionsRule
                        List<Transaction<?>> fraudTxs = ((RapidTransactionsRule) rule).getFraudTransactions(tx);
                        for (Transaction<?> fTx : fraudTxs) {
                            frauds.put(fTx, rule.getRuleName());
                        }
                    } else {
                        frauds.put(tx, rule.getRuleName());
                    }

                    break; // one rule is enough for one operation
                }
            }
        }
    }

    public Map<Transaction<?>, String> getFrauds() {

        return frauds;
    }
}