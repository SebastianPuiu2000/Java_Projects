package printer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import person.Consumer;
import person.Distributor;
import person.Producer;
import reader.Input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Result {

    private Result() {
    }

    /**
     * In aceasta metoda am afisat rezultatul dorit. Insa, inainte de a-l printa, i-am conferit
     * forma ceruta (ca in fisierele 'ref'), adica, cu alte cuvinte, am extras doar campurile care
     * erau necesare pentru afisare. Pentru o scriere frumoasa, am folosit "DefaultPrettyPrinter".
     */
    public static void print(final Input input, final String path) throws IOException {
        List<FinalConsumer> finalConsumers = new ArrayList<>();

        for (Consumer currentConsumer : input.getConsumers()) {
            finalConsumers.add(new FinalConsumer(currentConsumer.getId(),
                    currentConsumer.getBudget(),
                    currentConsumer.isBankrupt()));
        }

        List<FinalDistributor> finalDistributors = new ArrayList<>();

        for (Distributor currentDistributor : input.getDistributors()) {
            finalDistributors.add(new FinalDistributor(currentDistributor.getId(),
                    currentDistributor.getEnergyNeededKW(), currentDistributor.getContractCost(),
                    currentDistributor.getBudget(),
                    currentDistributor.getProducerStrategy(), currentDistributor.isBankrupt(),
                    currentDistributor.contracts));
        }

        List<FinalProducer> finalProducers = new ArrayList<>();

        for (Producer currentProducer : input.getProducers()) {
            finalProducers.add(new FinalProducer(currentProducer.getId(),
                    currentProducer.getMaxDistributors(),
                    currentProducer.getPriceKW(), currentProducer.getEnergyType(),
                    currentProducer.getEnergyPerDistributor(),
                    currentProducer.monthlyStats));
        }

        FinalData finalData = new FinalData(finalConsumers, finalDistributors, finalProducers);

        Object mapper = new ObjectMapper();
        ObjectWriter writer = ((ObjectMapper) mapper).writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(path), finalData);
    }
}
