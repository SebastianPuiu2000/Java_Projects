package person;

public final class Consumer extends Person {

    private final int id;
    private int budget;
    private final int monthlyIncome;
    private boolean bankrupt;
    private int distributorId;
    private long bill;
    private boolean hasContract;
    private boolean secondChance;
    private long debt;


    public Consumer(final int id, final int budget, final int monthlyIncome) {
        this.id = id;
        this.budget = budget;
        this.monthlyIncome = monthlyIncome;
        this.bankrupt = false;
        this.bill = 0;
        this.hasContract = false;
        this.secondChance = true;
        this.debt = 0;
    }

    public int getId() {
        return id;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getBudget() {
        return budget;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public long getBill() {
        return bill;
    }

    public void setBill(final long bill) {
        this.bill = bill;
    }

    public boolean isHasContract() {
        return hasContract;
    }

    public void setHasContract(final boolean hasContract) {
        this.hasContract = hasContract;
    }

    public boolean isSecondChance() {
        return secondChance;
    }

    public void setSecondChance(final boolean secondChance) {
        this.secondChance = secondChance;
    }

    public long getDebt() {
        return debt;
    }

    public void setDebt(final long debt) {
        this.debt = debt;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(final int distributorId) {
        this.distributorId = distributorId;
    }

    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", budget=" + budget
                + ", bankrupt=" + bankrupt
                + '}';
    }
}
