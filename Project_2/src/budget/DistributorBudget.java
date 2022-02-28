package budget;

import contract.Contract;
import person.Consumer;
import person.Distributor;
import reader.Input;

public final class DistributorBudget {

    private DistributorBudget() {
    }

    /**
     * In cadrul acestei metode, am calculat noul buget al distribuitorilor. Mai intai, la bugetul
     * lor am adaugat sumele ce trebuiau sa fie platite de toti consumatorii, in functie de
     * contractul fiecaruia. In cazul in care consumatorul nu a putut plati factura, m-am asigurat
     * ca nici distribuitorul lui nu isi va primii bani de la acesta. Apoi, am scazut din bugetul
     * fiecarui distributor cheltuielile pe care le avea de facut, acestea fiind influentate si
     * de numarul de contracte incheiate cu consumatorii.
     */
    public static void update(final Input input) {

        for (Distributor currentDistributor : input.getDistributors()) {
            for (Contract currentContract : currentDistributor.contracts) {
                for (Consumer currentConsumer : input.getConsumers()) {
                    if (currentConsumer.getId() == currentContract.getConsumerId()
                            && !currentConsumer.isBankrupt() && currentConsumer.isSecondChance()) {
                        currentDistributor.setBudget((int) (currentDistributor.getBudget()
                                + currentContract.getPrice()));
                    }
                }

            }
        }


        for (Distributor currentDistributor : input.getDistributors()) {
            if (currentDistributor.getBudget() - currentDistributor.getInfrastructureCost()
                    - currentDistributor.contracts.size()
                    * currentDistributor.getProductionCost() < 0) {

                if (!currentDistributor.isBankrupt()) {
                    currentDistributor.setBudget(currentDistributor.getBudget()
                            - currentDistributor.getInfrastructureCost());
                }
                currentDistributor.setBankrupt(true);

            } else if (!currentDistributor.isBankrupt()) {
                currentDistributor.setBudget(currentDistributor.getBudget()
                        - currentDistributor.getInfrastructureCost()
                        - currentDistributor.contracts.size()
                        * currentDistributor.getProductionCost());


            }

        }
    }
}
