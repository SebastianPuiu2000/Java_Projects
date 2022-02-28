package printer;

import java.util.List;

public class FinalData {
    public List<FinalConsumer> consumers;
    public List<FinalDistributor> distributors;
    public List<FinalProducer> energyProducers;

    public FinalData(final List<FinalConsumer> finalConsumers,
                     final List<FinalDistributor> finalDistributors,
                     final List<FinalProducer> energyProducers) {
        this.consumers = finalConsumers;
        this.distributors = finalDistributors;
        this.energyProducers = energyProducers;
    }
}
