package updater;

import observer.AffectedDistributors;
import observer.ProducerImpact;
import person.Consumer;
import person.Distributor;
import person.Producer;
import reader.Input;

public final class Month {

    private Month() {
    }

    /**
     * In cadrul acestei metode, am adaugat noii consumatori aferenti lunii date si, de asemenea,
     * tot aici am actualizat costurile pentru distribuitori.
     */
    public static void update(final Input input, final int i) {

        if (input.getNewDistributorCosts().get(i) != null) {
            for (DistributorUpdate costUpdate : input.getNewDistributorCosts().get(i)) {
                for (Distributor currentDistributor : input.getDistributors()) {
                    if (currentDistributor.getId() == costUpdate.getId()) {

                        currentDistributor.setInfrastructureCost(
                                costUpdate.getInfrastructureCost());
                    }
                }
            }
        }


        if (input.getNewConsumers().get(i) != null) {
            for (Consumer newConsumer : input.getNewConsumers().get(i)) {
                input.getConsumers().add(newConsumer);
            }
        }
    }

    /**
     * In cadrul acestei metode, am actualizat energia oferita de fiecare producator.
     */
    public static void updateProducer(final Input input, final int i) {

        ProducerImpact producerImpact = new ProducerImpact();
        producerImpact.addMyObserver(new AffectedDistributors(producerImpact));

        if (input.getNewProducerCosts().get(i) != null) {
            for (ProducerUpdate energyUpdate : input.getNewProducerCosts().get(i)) {
                for (Producer currentProducer : input.getProducers()) {
                    if (currentProducer.getId() == energyUpdate.getId()) {
                        producerImpact.setProducerIds(currentProducer, input.getDistributors(),
                                energyUpdate.getEnergyPerDistributor());
                    }
                }
            }
        }
    }
}
