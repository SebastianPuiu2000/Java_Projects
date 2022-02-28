package person;

import contract.Contract;

import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Person {

    private final int id;
    private final int contractLength;
    private int budget;
    private int energyNeededKW;
    private int productionCost;
    private String producerStrategy;
    private int infrastructureCost;
    private boolean bankrupt;
    private boolean hasContract;
    public List<Contract> contracts;
    private long contractCost;
    //public boolean producerChange;
    private List<Integer> producerIds;
    private boolean affected;


    public Distributor(final int id, final int contractLength, final int budget,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
        this.bankrupt = false;
        this.hasContract = false;
        this.contracts = new ArrayList<>();
        //this.producerChange = true;
        this.affected = true;

    }

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public long getContractCost() {
        return contractCost;
    }

    public void setContractCost(long contractCost) {
        this.contractCost = contractCost;
    }

    public boolean isAffected() {
        return affected;
    }

    public void setAffected(boolean affected) {
        this.affected = affected;
    }

    public List<Integer> getProducerIds() {
        return producerIds;
    }

    public void setProducerIds(List<Integer> producerIds) {
        this.producerIds = producerIds;
    }

    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + id
                + ", budget=" + budget
                + ", energyNeededKW=" + energyNeededKW
                + ", producerStrategy='" + producerStrategy + '\''
                + ", bankrupt=" + bankrupt
                + ", contracts=" + contracts
                + ", contractCost=" + contractCost
                + '}';
    }
}
