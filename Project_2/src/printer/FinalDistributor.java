package printer;

import contract.Contract;

import java.util.List;

public final class FinalDistributor {
    private int id;
    private int energyNeededKW;
    private long contractCost;
    private int budget;
    private String producerStrategy;
    public boolean isBankrupt;
    private List<Contract> contracts;


    public FinalDistributor(final int id, final int energyNeededKW, final long contractCost,
                            final int budget, final String producerStrategy,
                            final boolean isBankrupt,
                            final List<Contract> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public long getContractCost() {
        return contractCost;
    }

    public void setContractCost(long contractCost) {
        this.contractCost = contractCost;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
