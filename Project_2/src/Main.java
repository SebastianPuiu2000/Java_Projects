import bankrupt.BankruptConsumer;
import bankrupt.BankruptDistributor;
import budget.ConsumerBudget;
import budget.DistributorBudget;
import contest.DistributorContest;
import contest.ProducerContest;
import contract.ExistingContracts;
import contract.ExpiredContracts;
import person.Distributor;
import printer.Result;
import reader.Input;
import reader.InputLoader;
import updater.Month;
import updater.NewContracts;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() {
    }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {

        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readData();

        long bestPrice;
        Distributor bestDistributor;

        ProducerContest.begin(input, -1);

        bestPrice = DistributorContest.getBestPrice(input);
        bestDistributor = DistributorContest.getBestDistributor(input);

        NewContracts.sign(input, bestPrice, bestDistributor);

        ConsumerBudget.update(input);
        DistributorBudget.update(input);

        BankruptDistributor.removeContracts(input);
        BankruptConsumer.removeContracts(input);

        ExistingContracts.update(input);


        for (int i = 0; i < input.getNumberOfTurns(); i++) {

            Month.update(input, i);

            bestPrice = DistributorContest.getBestPrice(input);
            bestDistributor = DistributorContest.getBestDistributor(input);

            ExpiredContracts.remove(input);

            NewContracts.sign(input, bestPrice, bestDistributor);

            ConsumerBudget.update(input);
            DistributorBudget.update(input);
            Month.updateProducer(input, i);

            ProducerContest.begin(input, i);

            BankruptDistributor.removeContracts(input);
            BankruptConsumer.removeContracts(input);

            ExistingContracts.update(input);
        }

        Result.print(input, args[1]);
    }
}
