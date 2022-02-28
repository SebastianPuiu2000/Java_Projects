package observer;

import person.Distributor;

public final class AffectedDistributors extends MyObserver {

    public AffectedDistributors(ProducerImpact producerImpact) {
        this.producerImpact = producerImpact;
    }

    @Override
    public void update() {

        for (Distributor currentDistributor : producerImpact.getDistributors()) {
            if (currentDistributor.getProducerIds().contains(
                    producerImpact.getProducer().getId())) {
                currentDistributor.setAffected(true);
            }

        }

    }
}
