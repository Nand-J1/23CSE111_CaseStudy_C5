package Financial_Manager;

import java.io.FileWriter;
import java.io.IOException;

public class AlertManager {
    private final String ALERT_FILE = "alerts.txt";
    private void logAlert(String message) {
        try {
            FileWriter fw = new FileWriter(ALERT_FILE, true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void overspendingAlert() {
        String msg = "Overspending detected! Limit exceeded.";
        System.out.println(msg);
        logAlert(msg);
    }

    public void savingAlert() {
        String msg = "Savings goal reached!";
        System.out.println(msg);
        logAlert(msg);
    }

    public void spendingAlert(double spent, double budget) {
        if (spent >= 0.8 * budget) {
            String msg = "Spending reached 80% of budget.";
            System.out.println(msg);
            logAlert(msg);
        }
    }
}
