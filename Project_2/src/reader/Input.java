package reader;

import person.Consumer;
import person.Distributor;
import person.Producer;
import updater.DistributorUpdate;
import updater.ProducerUpdate;

import java.util.List;

public final class Input {
    private final long numberOfTurns;

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    private final List<Consumer> consumers;

    private final List<Distributor> distributors;

    private final List<Producer> producers;

    private final List<List<Consumer>> newConsumers;

    private final List<List<DistributorUpdate>> newDistributorCosts;

    private final List<List<ProducerUpdate>> newProducerCosts;

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<List<Consumer>> getNewConsumers() {
        return newConsumers;
    }


    public List<List<DistributorUpdate>> getNewDistributorCosts() {
        return newDistributorCosts;
    }

    public List<List<ProducerUpdate>> getNewProducerCosts() {
        return newProducerCosts;
    }

    public Input(final long numberOfTurns, final List<Consumer> consumers,
                 final List<Distributor> distributors, final List<Producer> producers,
                 final List<List<Consumer>> newConsumers,
                 final List<List<DistributorUpdate>> newDistributorCosts,
                 final List<List<ProducerUpdate>> newProducerCosts) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.newConsumers = newConsumers;
        this.newDistributorCosts = newDistributorCosts;
        this.newProducerCosts = newProducerCosts;
    }
}
