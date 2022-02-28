package contract;

import person.Distributor;
import reader.Input;

public final class ExistingContracts {

    private ExistingContracts() {
    }

    /**
     * In cadrul acestei metode, am actualizat numarul de luni ramase pentru fiecare contract.
     * (se scade cate o luna in fiecare runda)
     */
    public static void update(final Input input) {

        for (Distributor currentDistributor : input.getDistributors()) {
            for (Contract currentContract : currentDistributor.contracts) {
                currentContract.setRemainedContractMonths(
                        currentContract.getRemainedContractMonths() - 1);
            }
        }
    }

}
