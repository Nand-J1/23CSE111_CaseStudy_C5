package Financial_Manager;

import java.time.LocalDateTime;

public class Transaction {
    private static int count = 0;
    private String logID;
    private double amount;
    private Category category;
    private LocalDateTime date;

    public Transaction(double amount, Category category) {
        this.amount = amount;
        this.category = category;
        this.date = LocalDateTime.now();
        this.logID = "TXN-" + (++count);
    }

    public void getTransactionDetails() {
        System.out.println("Transaction ID: " + logID + " | Category: " + category.getCategoryName() +
                " | Amount: " + amount + " | Date: " + date);
    }

    public String getLogID() { return logID; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public LocalDateTime getDate() { return date; }
}
