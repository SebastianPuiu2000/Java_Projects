package updater;

public final class ProducerUpdate {

    private final int id;
    private final int energyPerDistributor;


    public ProducerUpdate(final int id, final int energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    @Override
    public String toString() {
        return "ProducerUpdate{"
                + "id=" + id
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }
}
