package util;

import exception.NegativeAmountException;
import exception.SuspiciousAccountException;
import model.Transaction;
import java.io.*;
import java.util.*;

public class FileManager {

    public static List<Transaction<Integer>> readTransactions(String filePath) {
        List<Transaction<Integer>> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // remove header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String account = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String time = parts[3];

                try {
                    if (amount < 0)
                        throw new NegativeAmountException("Negative amount → " + amount);
                    if (account.length() < 5 || account.length() > 15)
                        throw new SuspiciousAccountException("Incompatible account → " + account);
                } catch (NegativeAmountException | SuspiciousAccountException e) {
                    System.out.println("Fraud Exception: " + e.getMessage());
                }

                list.add(new Transaction<>(id, account, amount, time));
            }
        } catch (IOException e) {
            System.out.println("File nor readable: " + e.getMessage());
        }
        return list;
    }

    public static void writeFrauds(String filePath, Map<Transaction<?>, String> frauds) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists())
                parentDir.mkdirs();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<Transaction<?>, String> entry : frauds.entrySet()) {
                    bw.write(entry.getKey() + " → " + entry.getValue());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Typo error: " + e.getMessage());
        }
    }
}