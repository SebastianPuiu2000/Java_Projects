package strategies;

import person.Distributor;
import person.Producer;

import java.util.List;

public final class DistributorStrategy {

    /**
     * In cadrul acestei metode, fiecare distribuitor isi va putea alege producatorii in functie de
     * strategiile alese. De asemenea, aceasta metoda asigura ca doar distribuitorii ce au fost
     * afectati de schimbarea energiei producatorilor la care sunt abonati, vor fi cei care vor
     * putea sa isi aplice strategia din nou. Pentru runda initiala, am considerat ca toti
     * distribuitorii sunt afectati, pentru ca toti au nevoie sa isi aleaga producatori la inceput
     * de simulare.
     */
    public static void chooseProducer(final List<Distributor> distributorList,
                                       final List<Producer> producerList, final int i) {

        WantedStrategy wantedStrategy;

        for (Distributor currentDistributor : distributorList) {

            if (currentDistributor.isAffected()) {

                currentDistributor.setAffected(false);
                int index = -1;

                if (i != -1) {
                    for (Producer currentProducer : producerList) {
                        if (currentProducer.distributorIds.get(i + 1)
                                .contains(currentDistributor.getId())) {
                            for (int j = 0; j < currentProducer.distributorIds.get(i + 1).size();
                                 j++) {
                                if (currentProducer.distributorIds.get(i + 1).get(j)
                                        == currentDistributor.getId()) {
                                    index = j;
                                }
                            }
                            if (index != -1) {
                                currentProducer.distributorIds.get(i + 1).remove(index);
                            }
                        }
                    }
                }


                if (currentDistributor.getProducerStrategy()
                        .equals(EnergyChoiceStrategyType.GREEN.label)) {
                    wantedStrategy = new WantedStrategy(new Green());
                    wantedStrategy.executeStrategy(producerList, currentDistributor, i + 1);
                }

                if (currentDistributor.getProducerStrategy()
                        .equals(EnergyChoiceStrategyType.PRICE.label)) {
                    wantedStrategy = new WantedStrategy(new Price());
                    wantedStrategy.executeStrategy(producerList, currentDistributor, i + 1);
                }

                if (currentDistributor.getProducerStrategy()
                        .equals(EnergyChoiceStrategyType.QUANTITY.label)) {
                    wantedStrategy = new WantedStrategy(new Quantity());
                    wantedStrategy.executeStrategy(producerList, currentDistributor, i + 1);
                }

            }
        }


    }

}
