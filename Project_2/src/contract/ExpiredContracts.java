package contract;

import person.Consumer;
import person.Distributor;
import reader.Input;

import java.util.ArrayList;
import java.util.List;

public final class ExpiredContracts {

    private ExpiredContracts() {
    }

    /**
     * In cadrul acestei metode, am cautat, printre toti distribuitorii, toate contractele carora
     * le-a expirat valabilitatea (numarul de luni ramase a devenit 0), urmand ca mai apoi sa le
     * sterg din lista contractelor active.
     */
    public static void remove(final Input input) {

        List<Contract> expiredContracts;

        for (Distributor currentDistributor : input.getDistributors()) {
            expiredContracts = new ArrayList<>();

            for (Contract currentContract : currentDistributor.contracts) {
                if (currentContract.getRemainedContractMonths() == 0) {

                    expiredContracts.add(currentContract);

                    for (Consumer currentConsumer : input.getConsumers()) {
                        if (currentConsumer.getId() == currentContract.getConsumerId()) {
                            currentConsumer.setHasContract(false);
                        }
                    }

                }
            }

            for (Contract currentExpiredContract : expiredContracts) {
                currentDistributor.contracts.remove(currentExpiredContract);
            }
        }
    }
}
