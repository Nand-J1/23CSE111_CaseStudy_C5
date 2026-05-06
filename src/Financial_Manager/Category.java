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

    public boolean withdrawMoney(double b) {
        if (spent+b >= budget) {
        	new AlertManager().overspendingAlert();
        	return false;
    }else {
    	spent += b;
    	return true;
    }}

    public void addMoney(double b) {
     if(b>0) {
    	 budget=budget+b;
    	 }
     }
    public void restoreSpent(double s) {
        this.spent = s;
    }

    public String getCategoryName() { return categoryName; }
    public double getBudget() { return budget; }
    public double getSpent() { return spent; }
}
