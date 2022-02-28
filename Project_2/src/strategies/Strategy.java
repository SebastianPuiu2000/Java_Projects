package strategies;

import person.Distributor;
import person.Producer;

import java.util.List;

public interface Strategy {

    /**
     * Aceasta metoda duce la alegerea unui producatorilor (din lista de producatori disponibili)
     * pentru un distribuitor, in luna curenta, in functie de cerintele sale.
     */
    void selectProducer(List<Producer> producers, Distributor distributor, Integer month);
}
