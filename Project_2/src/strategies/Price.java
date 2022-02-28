package strategies;

import person.Distributor;
import person.Producer;

import java.util.ArrayList;
import java.util.List;

public class Price implements Strategy {

    public static final int DIVISOR = 10;
    public static final long MAX = 10000;

    /**
     * In cadrul acestei metode, am ales pentru distribuitor producatorii care ofera cel mai bun
     * pret, pana cand distribuitorul facea rost de toata energia necesara. In cazul in care doi
     * producatori ofera acelasi pret, va fi ales cel ce furnizeaza mai multa energie.
     */
    @Override
    public void selectProducer(List<Producer> producers, Distributor distributor, Integer month) {


        int neededEnergy = distributor.getEnergyNeededKW();
        int cost = 0;
        List<Integer> producerIds = new ArrayList<>();


        while (neededEnergy > 0) {

            int mostEnergy = 0;
            double bestPrice = MAX;
            Producer bestProducer = null;

            for (Producer currentProducer : producers) {
                if (currentProducer.getPriceKW() < bestPrice) {
                    if (currentProducer.getMaxDistributors()
                            > currentProducer.distributorIds.get(month).size()) {
                        if (!producerIds.contains(currentProducer.getId())) {
                            bestPrice = currentProducer.getPriceKW();
                        }
                    }
                }
            }


            for (Producer currentProducer : producers) {
                if (currentProducer.getPriceKW() == bestPrice) {
                    if (currentProducer.getMaxDistributors()
                            > currentProducer.distributorIds.get(month).size()) {
                        if (!producerIds.contains(currentProducer.getId())) {
                            if (mostEnergy < currentProducer.getEnergyPerDistributor()) {
                                bestProducer = currentProducer;
                                mostEnergy = currentProducer.getEnergyPerDistributor();
                            }
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
