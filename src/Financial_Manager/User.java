package Financial_Manager;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
public class User {
	private String name;
	private String job;
	private String password;
	private ArrayList<Category> categories;
	private ArrayList<Transaction> transactions;
	private Savings savings;
	private double bonus;
	private double income;
	private double budget;
	
	public User(String name, String job,String password,double income) {
		this.name=name;
		this.job=job;
		this.password=password;
		this.income=income;
		this.categories=new ArrayList<Category>();
		this.transactions=new ArrayList<Transaction>();
		this.savings = new Savings(name);
	}
	public void setBudget(double x) {
		if (x>(income+bonus)) {
			System.out.println("Your budget is above your daily income.");
		}else {
			budget=x;
			System.out.println("Your budget is set.");
			saveUserUpdate();
		}
	}
	
	//functions related to category
	public void addCategory(String s,double b) {
		Category newC=new Category(s,b);
		categories.add(newC);
		saveCategory(newC);
		System.out.println("New Category "+s+" is added.");
	}
	private void saveCategory(Category c) {
        try (FileWriter fw = new FileWriter(name + "_categories.txt", true)) {
            fw.write(c.getCategoryName() + "," + c.getBudget() + "," + c.getSpent() + "\n");
        } catch (IOException e) { e.printStackTrace(); }
    }
	public void loadCategories() {
        categories.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(name + "_categories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Category c = new Category(parts[0], Double.parseDouble(parts[1]));
                c.restoreSpent(Double.parseDouble(parts[2]));
                categories.add(c);
            }
        } catch (IOException e) { 
        	System.out.println("There is a small problem with loading the categories.");
        }
    }
	public void updateCategoryFile() {
	    try (FileWriter fw = new FileWriter(name + "_categories.txt")) {
	        for (Category cat : categories) {
	            fw.write(cat.getCategoryName() + "," + cat.getBudget() + "," + cat.getSpent() + "\n");
	        }
	    } catch (IOException e) { e.printStackTrace(); }
	}

	//Transaction 
	public void showTransactionLog() {
		for(Transaction t:transactions) {
			t.getTransactionDetails();
		}
	}
	public void saveUserUpdate() {
	    File inputFile = new File("users.txt");
	    File tempFile = new File("users_temp.txt");

	    try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
	         PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            
	            if (parts[0].equalsIgnoreCase(this.name)) {
	                pw.println(name + "," + job + "," + password + "," + income + "," + budget);
	            } else {
	                pw.println(line);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    
	    if (inputFile.delete()) {
	        tempFile.renameTo(inputFile);
	    }
	}
	public Category findCategory(String cat) {
		for(Category c:categories) {
			if( c.getCategoryName().equalsIgnoreCase(cat)){
				return c;
			}
		}return null;
		}
	public void withdrawMoney(double amount, String s) {
        Category cat = findCategory(s);
        if (cat != null) {
			if (cat.withdrawMoney(amount)==true){
            	Transaction txn = new Transaction(amount, cat);
            transactions.add(txn);
            saveTransaction(txn);
            updateCategoryFile();}
            new AlertManager().spendingAlert(cat.getSpent(), cat.getBudget());
        } else {
            System.out.println("Category does not exist.");
        }
    }
	private void saveTransaction(Transaction t) {
        try (FileWriter fw = new FileWriter(name + "_transactions.txt", true)) {
            fw.write(t.getLogID() + "," + t.getCategory().getCategoryName() + "," + t.getAmount() + "," + t.getDate() + "\n");
        } catch (IOException e) { e.printStackTrace(); }
    }
	public void loadTransactions() {
        transactions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(name + "_transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Category cat = findCategory(parts[1]);
                if (cat != null) {
                    transactions.add(new Transaction(Double.parseDouble(parts[2]), cat));
                }
            }
        } catch (IOException e) { /* ignore if file empty */ }
    }
	public void addMoney(double b,String s) {
		Category cat=findCategory(s);
		if (cat!=null) {
			cat.addMoney(b);
			updateCategoryFile();
		}else {
			System.out.println("Category does not exist.");
		}
	}
	public void showCategories() {
		int l=categories.size();
		for (int i=0;i<l;i++) {
			System.out.println(i+". "+categories.get(i).getCategoryName());
		}
	}
		
	public void viewUserDetails() {
		System.out.println("The detail given are:");
		System.out.println("NAME:\t"+name);
		System.out.println("JOB:\t"+job);
		System.out.println("INCOME:\t"+income);
		System.out.println("BUDGET:\t"+budget);
		if (bonus>0){
			System.out.println("BONUS:\t"+bonus);}
		System.out.println();
	}
	
	public void getExpenseChart(Stage stage, int opt) {
		javafx.application.Platform.runLater(() -> {
        Map<String, Double> data = new HashMap<>();
        for (Category c : categories) {
            data.put(c.getCategoryName(), c.getSpent());
        }

        ChartManager chartManager = new ChartManager();
        if (opt == 1) {
            chartManager.createPieChart(data);
            chartManager.displayPieChart(stage);
        } else if (opt == 2) {
            chartManager.createBarGraph(data);
            chartManager.displayBarGraph(stage);
        } else if (opt == 3) {
            chartManager.createPieChart(data);
            chartManager.createBarGraph(data);
            chartManager.displayBothCharts(stage);
        } else {
            System.out.println("Option not available.");
        }});
    }
	public void withdrawFromSavings(double amount) {
	    if (savings != null) savings.withdrawSaving(amount);
	}
	public void addToSavings(double amount) {
	    if (savings != null) savings.addSaving(amount);
	}
	public double getSavingsTotal() {
		if (savings != null){
			return savings.getSaving();}
		else {
			return 0;
		}
	}
	public String getName() {
		return name;
	}
	public String getJob() {
		return job;}
	public double getIncome() {
		return income;}
	public String getPassword() { return password; }
    public ArrayList<Category> getCategories() { return categories; }
    public ArrayList<Transaction> getTransactions() { return transactions; }

	
}

