package contest;

import person.Distributor;
import reader.Input;

public final class DistributorContest {

    public static final double COST = 0.2;
    public static final long MAX = 10000;

    private DistributorContest() {
    }

    /**
     * In aceasta metoda, am aflat care este cel mai bun dintre distribuitorii lunii curente. Am
     * folosit ca si criteriu de selectie pretul contractului oferit de fiecare. Prin urmare,
     * castigatorul concursului era cel care oferea cel mai bun(ieftin) pret.
     */
    public static Distributor getBestDistributor(final Input input) {

        long contractPrice;
        long profit;
        long smallestPrice = MAX;
        Distributor bestDistributor = null;

        for (Distributor currentDistributor : input.getDistributors()) {
            if (!currentDistributor.isBankrupt()) {
                if (currentDistributor.contracts.size() == 0) {
                    profit = Math.round(Math.floor(COST * currentDistributor.getProductionCost()));
                    contractPrice = currentDistributor.getInfrastructureCost()
                            + currentDistributor.getProductionCost() + profit;
                } else {
                    profit = Math.round(Math.floor(COST * currentDistributor.getProductionCost()));
                    contractPrice = Math.round(Math.floor(currentDistributor.getInfrastructureCost()
                            / currentDistributor.contracts.size())
                            + currentDistributor.getProductionCost() + profit);
                }

                if (smallestPrice > contractPrice) {
                    smallestPrice = contractPrice;
                    bestDistributor = currentDistributor;
                }
            }
        }
        return bestDistributor;
    }

    /**
     * In aceasta metoda, am aflat care este cel mai bun(ieftin) pret oferit de distribuitorii lunii
     * curente. Pentru a putea obtine acest rezultat, am folosit formulele de calcul oferite in
     * cadrul cerintei.
     */
    public static long getBestPrice(final Input input) {

        long contractPrice;
        long profit;
        long smallestPrice = MAX;

        for (Distributor currentDistributor : input.getDistributors()) {
            if (!currentDistributor.isBankrupt()) {
                if (currentDistributor.contracts.size() == 0) {
                    profit = Math.round(Math.floor(COST * currentDistributor.getProductionCost()));
                    contractPrice = currentDistributor.getInfrastructureCost()
                            + currentDistributor.getProductionCost() + profit;
                } else {
                    profit = Math.round(Math.floor(COST * currentDistributor.getProductionCost()));
                    contractPrice = Math.round(Math.floor(currentDistributor.getInfrastructureCost()
                            / currentDistributor.contracts.size())
                            + currentDistributor.getProductionCost() + profit);
                }

                if (smallestPrice > contractPrice) {
                    smallestPrice = contractPrice;
                }

                currentDistributor.setContractCost(contractPrice);
            }

        }
        return smallestPrice;
    }
}
