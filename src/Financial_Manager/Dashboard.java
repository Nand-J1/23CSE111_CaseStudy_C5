package Financial_Manager;

import javafx.application.*;
import javafx.stage.Stage;
import java.util.Scanner;


public class Dashboard extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        Platform.setImplicitExit(false); 
        // Starts the menu as a background thread
        Thread menuThread = new Thread(() -> runTerminalMenu(mainStage));
        menuThread.setDaemon(true); 
        menuThread.start();
    }
    private void runTerminalMenu(Stage stage) {
        Scanner sc = new Scanner(System.in);
        AuthorizeUser auth = new AuthorizeUser();
        User user = null;
        System.out.println("PERSONAL FINANCIAL MANAGER");
        System.out.println("--------------------------");
        System.out.println("1. Register\n2. Login");
        System.out.println("Enter option:");
        int opt = sc.nextInt(); 
        sc.nextLine();
        if (opt == 1) {
            System.out.print("Name: "); String name = sc.nextLine();
            System.out.print("Job: "); String job = sc.nextLine();
            System.out.print("Password: "); String pass = sc.nextLine();
            System.out.print("Income: "); double income = sc.nextDouble();
            user = new User(name, job, pass, income);
            auth.registerUser(user);
        } else if (opt == 2) {
            System.out.print("Name: "); String name = sc.nextLine();
            System.out.print("Password: "); String pass = sc.nextLine();
            user = auth.loginUser(name, pass);
        }

        if (user == null) return;

        boolean running = true;
        while (running) {
            System.out.println("\n--- Dashboard Menu ---");
            System.out.println("1. Add Category");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Show Categories");
            System.out.println("4. Show Transactions");
            System.out.println("5. View Expense Chart");
            System.out.println("6. View User Details");
            System.out.println("7. Savings");
            System.out.println("8.. Add Money");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Category name: ");
                    String cname = sc.nextLine();
                    System.out.print("Budget: ");
                    double budget = sc.nextDouble();
                    user.addCategory(cname, budget);
                    break;
                case 2:
                    System.out.print("Category name: ");
                    String wcat = sc.nextLine();
                    System.out.print("Amount: ");
                    double wamt = sc.nextDouble();
                    user.withdrawMoney(wamt, wcat);
                    break;
                case 3:
                    user.loadCategories();
                    for (Category c : user.getCategories()) {
                        System.out.println(c.getCategoryName() + " | Budget: " + c.getBudget() + " | Spent: " + c.getSpent());
                    }
                    break;
                case 4:
                    user.loadTransactions();
                    for (Transaction t : user.getTransactions()) {
                        t.getTransactionDetails();
                    }
                    break;
                case 5:
                    System.out.println("Choose chart type: 1. Pie  2. Bar  3. Both");
                    int optChart = sc.nextInt();
                    user.getExpenseChart(stage, optChart);
                    break;
                case 6:
                	user.viewUserDetails();
                	break;
                case 7:
                	System.out.println("1. Add Savings");
                    System.out.println("2. Withdraw Savings");
                    System.out.println("3. Show Savings");
                    System.out.print("Enter savings goal: ");
                    int sopt = sc.nextInt();
                    sc.nextLine();
                    switch(sopt) {
	                    case 1:
	                        System.out.print("Enter amount to save: ");
	                        double saveAmt = sc.nextDouble();
	                        user.addToSavings(saveAmt);
	                        break;
	                    case 2:
	                        System.out.print("Enter amount to withdraw from savings: ");
	                        double withdrawAmt = sc.nextDouble();
	                        user.withdrawFromSavings(withdrawAmt);
	                        break;
	                    case 3:
	                        System.out.println("Total Savings: " + user.getSavingsTotal());
	                        break;
	                    }
                case 8:
                	System.out.println("Enter Category:");
                	String c=sc.nextLine();
                	System.out.print("Enter amount: ");
                    double b = sc.nextDouble();
                    sc.nextLine();
                	user.addMoney(b, c);
                case 9:
                    running = false;
                    System.out.println("Exiting...");
                    break;    
                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
