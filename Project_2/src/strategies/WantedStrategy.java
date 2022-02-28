package strategies;

import person.Distributor;
import person.Producer;

import java.util.List;

public class WantedStrategy {

    private Strategy strategy;

    public WantedStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * In cadrul acestei metode, se va executa strategia aleasa de un distribuitor pentru alegerea
     * producatorilor (din lista de producatori disponibili).
     */
    public void executeStrategy(List<Producer> producers, Distributor distributor, Integer month) {
        strategy.selectProducer(producers, distributor, month);
    }
}
