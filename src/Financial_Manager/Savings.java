package Financial_Manager;

import java.io.*;
import java.io.IOException;

public class Savings {
    private double saving;
    private String SAVINGS_FILE = "savings.txt";
    public Savings(String username) {
        this.SAVINGS_FILE = username + "_savings.txt";
        loadSavings(); 
    }
    public void addSaving(double b) {
        saving += b;
        System.out.println("Added saving: " + b + " | Total: " + saving);
        saveSavings();
        if (saving >= 1000) new AlertManager().savingAlert();
    }
    public void withdrawSaving(double b) {
        if (b <= saving) {
            saving -= b;
            System.out.println("Withdrawn: " + b + " | Remaining: " + saving);
            saveSavings();
        } else {
            System.out.println("Not enough savings!");
        }
    }

    private void saveSavings() {
        try {
            FileWriter fw = new FileWriter(SAVINGS_FILE, false); 
            fw.write("TotalSavings," + saving + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadSavings() {
        try (BufferedReader br = new BufferedReader(new FileReader(SAVINGS_FILE))) {
            String line = br.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                saving = Double.parseDouble(parts[1]);
            }
        } catch (IOException e) {
            saving = 0; 
        }
    }

    public double getSaving() { return saving; }
}
