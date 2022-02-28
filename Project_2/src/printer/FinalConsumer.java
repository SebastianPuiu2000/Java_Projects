package printer;

public final class FinalConsumer {

    private int id;
    public boolean isBankrupt;
    private int budget;

    public FinalConsumer(final int id, final int budget, final boolean isBankrupt) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
