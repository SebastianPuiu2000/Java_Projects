package strategies;

import person.Distributor;
import person.Producer;

import java.util.ArrayList;
import java.util.List;

public class Quantity implements Strategy {

    public static final int DIVISOR = 10;

    /**
     * In cadrul acestei metode, am ales pentru distribuitor producatorii care ofera cea mai mare
     * cantitate de energie, pana cand distribuitorul facea rost de toata energia necesara.
     */
    @Override
    public void selectProducer(List<Producer> producers, Distributor distributor, Integer month) {

        int neededEnergy = distributor.getEnergyNeededKW();
        int cost = 0;
        List<Integer> producerIds = new ArrayList<>();

        while (neededEnergy > 0) {

            int mostEnergy = 0;
            Producer bestProducer = null;

            for (Producer currentProducer : producers) {
                if (currentProducer.getEnergyPerDistributor() > mostEnergy) {
                    if (currentProducer.getMaxDistributors()
                            > currentProducer.distributorIds.get(month).size()) {
                        if (!producerIds.contains(currentProducer.getId())) {
                            mostEnergy = currentProducer.getEnergyPerDistributor();
                            bestProducer = currentProducer;

                        }
                    }
                }
            }

            if (bestProducer != null) {
                producerIds.add(bestProducer.getId());
                neededEnergy = neededEnergy - bestProducer.getEnergyPerDistributor();
                cost =
                        (int) (cost
                                + bestProducer.getEnergyPerDistributor()
                                * bestProducer.getPriceKW());
            }
        }

        distributor.setProducerIds(producerIds);
        distributor.setProductionCost(cost / DIVISOR);

        for (Producer currentProducer : producers) {
            for (Integer producerId : producerIds) {
                if (currentProducer.getId() == producerId) {
                    currentProducer.distributorIds.get(month).add(distributor.getId());
                }
            }
        }
    }
}
