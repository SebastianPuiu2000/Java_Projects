package reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import person.Consumer;
import person.Distributor;
import person.PersonFactory;
import person.Producer;
import updater.DistributorUpdate;
import updater.ProducerUpdate;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputLoader {
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     *
     *
     */

    public Input readData() {
        JSONParser jsonParser = new JSONParser();
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<List<Consumer>> newMonthlyConsumers = new ArrayList<>();
        List<Consumer> monthlyConsumers;

        List<DistributorUpdate> monthlyDistributorUpdates;
        List<ProducerUpdate> monthlyProducerUpdates;

        List<List<DistributorUpdate>> newMonthlyDistributorCost = new ArrayList<>();
        List<List<ProducerUpdate>> newMonthlyProducerCost = new ArrayList<>();
        long numberOfTurns = 0;

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));
            numberOfTurns = (long) jsonObject.get("numberOfTurns");
            JSONObject initialData = (JSONObject) jsonObject.get("initialData");
            JSONArray monthlyUpdateDatas = (JSONArray) jsonObject.get("monthlyUpdates");

            JSONArray jsonConsumers = (JSONArray) initialData.get("consumers");
            JSONArray jsonDistributors = (JSONArray) initialData.get("distributors");
            JSONArray jsonProducers = (JSONArray) initialData.get("producers");

            JSONObject consumer;
            JSONObject distributor;
            JSONObject producer;
            JSONArray newConsumers;
            JSONArray distributorChanges;
            JSONArray producerChanges;
            JSONObject update;

            PersonFactory personFactory = PersonFactory.getInstance();

            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumer = (JSONObject) jsonConsumer;
                    consumers.add((Consumer) personFactory.getPerson("consumer", consumer));
                }
            } else {
                System.out.println("Nu exista consumatori");
            }

            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    distributor = (JSONObject) jsonDistributor;
                    distributors
                            .add((Distributor) personFactory.getPerson("distributor", distributor));
                }
            } else {
                System.out.println("Nu exista distributori");
            }

            if (jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    producer = (JSONObject) jsonProducer;
                    producers
                            .add((Producer) personFactory.getPerson("producer", producer));
                }
            } else {
                System.out.println("Nu exista producatori");
            }


            for (Object updateData : monthlyUpdateDatas) {

                JSONObject monthlyUpdateData = (JSONObject) updateData;
                newConsumers = (JSONArray) monthlyUpdateData.get("newConsumers");
                monthlyConsumers = new ArrayList<>();

                for (Object newConsumer : newConsumers) {
                    consumer = (JSONObject) newConsumer;
                    monthlyConsumers.add((Consumer) personFactory.getPerson("consumer", consumer));
                }

                newMonthlyConsumers.add(monthlyConsumers);

                distributorChanges = (JSONArray) monthlyUpdateData.get("distributorChanges");
                monthlyDistributorUpdates = new ArrayList<>();

                for (Object newUpdate : distributorChanges) {
                    update = (JSONObject) newUpdate;
                    monthlyDistributorUpdates.add(new DistributorUpdate(
                            Integer.parseInt(update.get("id").toString()),
                            Integer.parseInt(update.get("infrastructureCost").toString())));
                }

                newMonthlyDistributorCost.add(monthlyDistributorUpdates);


                producerChanges = (JSONArray) monthlyUpdateData.get("producerChanges");
                monthlyProducerUpdates = new ArrayList<>();

                for (Object producerChange : producerChanges) {
                    update = (JSONObject) producerChange;
                    monthlyProducerUpdates
                            .add(new ProducerUpdate(Integer.parseInt(update.get("id").toString()),
                                    Integer.parseInt(
                                            update.get("energyPerDistributor").toString())));
                }

                newMonthlyProducerCost.add(monthlyProducerUpdates);

            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(numberOfTurns, consumers, distributors, producers, newMonthlyConsumers,
                newMonthlyDistributorCost, newMonthlyProducerCost);
    }
}
