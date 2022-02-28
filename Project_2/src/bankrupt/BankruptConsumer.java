package bankrupt;

import contract.Contract;
import person.Consumer;
import person.Distributor;
import reader.Input;

public final class BankruptConsumer {

    private BankruptConsumer() {
    }

    /**
     * In cadrul acestei metode, in cazul in care un consumator era declarat falimentar, cautam
     * distribuitorul la care acesta avea contract si il eliminam din lista.
     */
    public static void removeContracts(final Input input) {
        Contract expiredContract;

        for (Consumer currentConsumer : input.getConsumers()) {
            if (currentConsumer.isBankrupt()) {
                for (Distributor currentDistributor : input.getDistributors()) {
                    expiredContract = null;
                    for (Contract currentContract : currentDistributor.contracts) {
                        if (currentConsumer.getId() == currentContract.getConsumerId()) {
                            expiredContract = currentContract;
                        }
                    }

                    currentDistributor.contracts.remove(expiredContract);
                }
            }
        }
    }
}
