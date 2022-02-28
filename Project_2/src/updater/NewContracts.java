package updater;

import person.Consumer;
import person.Distributor;
import contract.Contract;
import reader.Input;

public final class NewContracts {

    private NewContracts() {
    }


    /**
     * Prin intermediul acestei metode, toti consumatorii care nu au incheiat niciun contract si
     * nu sunt declarati falimentari, vor semna noi contracte cu cel mai bun distribuitor (cel care
     * a castigat concursul. din luna curenta, prin oferirea celui mai bun pret de contract)
     */
    public static void sign(final Input input, final long bestPrice,
                            final Distributor bestDistributor) {

        for (Consumer currentConsumer : input.getConsumers()) {
            if (!currentConsumer.isHasContract() && !currentConsumer.isBankrupt()
                    && bestDistributor != null) {

                bestDistributor.contracts.add(new Contract(currentConsumer.getId(), bestPrice,
                        bestDistributor.getContractLength()));
                currentConsumer.setDistributorId(bestDistributor.getId());
                currentConsumer.setHasContract(true);
                currentConsumer.setBill(bestPrice);

            }
        }
    }
}
