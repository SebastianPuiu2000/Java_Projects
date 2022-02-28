package strategies;

import entities.EnergyType;
import person.Distributor;
import person.Producer;

import java.util.ArrayList;
import java.util.List;

public class Green implements Strategy {

    public static final int DIVISOR = 10;
    public static final long MAX = 10000;

    /**
     * In cadrul acestei metode, am ales pentru distribuitor producatorii care ofera energie de tip
     * regenerabil. Acestia au fost alesi in ordinea celor mai bune preturi, dupa care in ordinea
     * celor mai mari cantitati de energie furnizate. In cazul in care distribuitorul inca mai avea
     * nevoie de energie, am reluat acest proces si pentru producatorii ce nu ofereau energie Green.
     */
    @Override
    public void selectProducer(List<Producer> producers, Distributor distributor, Integer month) {

        int neededEnergy = distributor.getEnergyNeededKW();
        int cost = 0;

        List<Producer> greenProducers = new ArrayList<>();
        List<Integer> producerIds = new ArrayList<>();

        for (Producer currentProducer : producers) {
            if (currentProducer.getEnergyType().equals(EnergyType.WIND.getLabel())
                    || currentProducer.getEnergyType().equals(EnergyType.HYDRO.getLabel())
                    || currentProducer.getEnergyType().equals(EnergyType.SOLAR.getLabel())) {
                greenProducers.add(currentProducer);
            }
        }

        int i = 0;
        while (i < greenProducers.size()) {

            double bestPrice = MAX;
            Producer bestProducer = null;
            int mostEnergy = 0;

            if (neededEnergy > 0) {

                for (Producer currentProducer : greenProducers) {
                    if (currentProducer.getPriceKW() < bestPrice) {
                        if (currentProducer.getMaxDistributors()
                                > currentProducer.distributorIds.get(month).size()) {
                            if (!producerIds.contains(currentProducer.getId())) {
                                bestPrice = currentProducer.getPriceKW();
                                bestProducer = currentProducer;
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
                                    mostEnergy = currentProducer.getEnergyPerDistributor();
                                    bestProducer = currentProducer;
                                }
                            }
                        }
                    }
                }

                if (bestProducer != null) {

                    producerIds.add(bestProducer.getId());
                    neededEnergy = neededEnergy - bestProducer.getEnergyPerDistributor();
                    cost =
                            (int) (cost + bestProducer.getEnergyPerDistributor()
                                    * bestProducer.getPriceKW());
                }
            }
            i++;
        }


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
                                mostEnergy = currentProducer.getEnergyPerDistributor();
                                bestProducer = currentProducer;
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
