package Financial_Manager;

import java.io.*;

public class AuthorizeUser {
    private final String USER_FILE = "users.txt";

    // Registering user 
    public boolean registerUser(User u) {
        try (FileWriter fw = new FileWriter(USER_FILE, true)) {
            fw.write(u.getName() + "," + u.getJob() + "," + u.getPassword() + "," + u.getIncome() + "\n");
        } catch (IOException e) { e.printStackTrace(); return false; }

        // Create empty category and transaction files for individual user
        try {
            new File(u.getName() + "_categories.txt").createNewFile();
            new File(u.getName() + "_transactions.txt").createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("User is registered.");
        return true;
    }

    // Load user from file
    public User loginUser(String name, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(name) && parts[2].equals(password)) {
                    System.out.println("Login successful for " + name);
                    User u = new User(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                    u.loadCategories();
                    u.loadTransactions();
                    return u;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("Login failed (try logging in again with correct username and password or register).");
        return null;
    }
}
