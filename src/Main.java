import model.Transaction;
import service.*;
import util.FileManager;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "data/transactions.csv";
        String outputFile = "reports/fraud_report.txt";

        List<Transaction<Integer>> transactions = FileManager.readTransactions(inputFile);

        FraudDetector detector = new FraudDetector();
        detector.addRule(new AmountRule());
        detector.addRule(new DuplicateRule());
        detector.addRule(new NightTransactionRule());
        detector.addRule(new RapidTransactionsRule());

        detector.detect(transactions);

        Map<Transaction<?>, String> frauds = detector.getFrauds();
        FileManager.writeFrauds(outputFile, frauds);

        System.out.println("Total operations: " + transactions.size());
        System.out.println("Suspicious operations: " + frauds.size());
    }
}