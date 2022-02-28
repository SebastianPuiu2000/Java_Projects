package updater;

public final class DistributorUpdate {

    private final int id;
    private final int infrastructureCost;


    public DistributorUpdate(final int id, final int infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }


    @Override
    public String toString() {
        return "DistributorUpdate{"
                + "id=" + id
                + ", infrastructureCost=" + infrastructureCost
                + '}';
    }
}
