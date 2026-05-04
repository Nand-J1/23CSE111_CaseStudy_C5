package Financial_Manager;

public class Category {
    private String categoryName;
    private double budget;
    private double spent;

    public Category(String name, double budget) {
        this.categoryName = name;
        this.budget = budget;
        this.spent = 0;
    }

    public void withdrawMoney(double b) {
        if (spent > budget) {
        	new AlertManager().overspendingAlert();
    }else {
    	spent += b;
    	new AlertManager().spendingAlert(spent, budget);

    }}

    public void addMoney(double b) {
        spent -= b;
        if (spent < 0) spent = 0;
    }

    public void restoreSpent(double s) {
        this.spent = s;
    }

    public String getCategoryName() { return categoryName; }
    public double getBudget() { return budget; }
    public double getSpent() { return spent; }
}
