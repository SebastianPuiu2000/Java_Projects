package contest;

import person.Producer;
import reader.Input;
import strategies.DistributorStrategy;
import updater.MonthlyStat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProducerContest {

    /**
     * Prin intermediul acestei metode, are loc concursul producatorilor ce vor fi alesi pentru
     * fiecare distribuitor, in functie de strategia dorita. Daca metoda este apelata in runda
     * initiala, atunci va avea loc si oetapa de initializare a listelor de distribuitori pentru
     * fiecare producator. La sfarsitul concursului, producatorii vor primi distribuitorii ce i-au
     * ales pe luna curenta, acestia fiind ordonati crescator dupa ID.
     */
    public static void begin(final Input input, final int i) {


        if (i == -1) {
            for (int j = 0; j <= input.getNumberOfTurns(); j++) {
                for (Producer currentProducer : input.getProducers()) {
                    currentProducer.distributorIds.put(j, new ArrayList<>());
                }
            }
            DistributorStrategy.chooseProducer(input.getDistributors(), input.getProducers(), i);
        } else {
            for (Producer currentProducer : input.getProducers()) {
                for (Integer distributorId : currentProducer.distributorIds.get(i)) {
                    currentProducer.distributorIds.get(i + 1)
                            .add(distributorId);
                }
            }

            DistributorStrategy.chooseProducer(input.getDistributors(), input.getProducers(), i);

            List<Integer> copy;

            for (Producer currentProducer : input.getProducers()) {
                copy = currentProducer.distributorIds.get(i + 1);
                Collections.sort(copy);
                currentProducer.monthlyStats.add(new MonthlyStat(i + 1, copy));
            }
        }
    }
}
