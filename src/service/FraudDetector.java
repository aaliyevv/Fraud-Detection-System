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

                