package model;

public class Transaction<T> {
    private T id;
    private String accountNumber;
    private double amount;
    private String time; // HH:mm

    public Transaction(T id, String accountNumber, double amount, String time) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
    }

    public T getId() {
        return id; }

