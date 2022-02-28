package bankrupt;

import contract.Contract;
import person.Consumer;
import person.Distributor;
import reader.Input;

import java.util.ArrayList;
import java.util.List;

public final class BankruptDistributor {

    private BankruptDistributor() {
    }

    /**
     * In cadrul acestei metode, am eliminat toate contractele distribuitorilor ce au dat faliment.
     * De asemenea, m-am asigurat si ca toti consumatorii ramasi fara contract vor fi disponibili
     * pentru a semna un nou contract.
     */
    public static void removeContracts(final Input input) {
        List<Contract> expiredContracts;

        for (Distributor currentDistributor : input.getDistributors()) {
            expiredContracts = new ArrayList<>();
            if (currentDistributor.isBankrupt()) {
                for (Contract currentContract : currentDistributor.contracts) {
                    expiredContracts.add(currentContract);
                }

                for (Contract expiredContract : expiredContracts) {
                    currentDistributor.contracts.remove(expiredContract);
                    for (Consumer currentConsumer : input.getConsumers()) {
                        if (expiredContract.getConsumerId() == currentConsumer.getId()) {
                            currentConsumer.setHasContract(false);
                        }
                    }
                }
            }

        }
    }
}
