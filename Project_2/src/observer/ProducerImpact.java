package observer;

import person.Distributor;
import person.Producer;;

import java.util.ArrayList;
import java.util.List;

public final class ProducerImpact {

    private List<MyObserver> myObservers = new ArrayList<>();
    private MyObserver myObserver;
    private Producer producer;
    private List<Distributor> distributors;

    public Producer getProducer() {
        return producer;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }


    /**
     * In cadrul acestei metode, este setata noua energie oferita de catre producator si, in plus,
     * este notificat observatorul ce tine cont de aceste modificari.
     */
    public void setProducerIds(Producer producer, List<Distributor> distributors, int newEnergy) {
        this.producer = producer;
        this.distributors = distributors;
        this.producer.setEnergyPerDistributor(newEnergy);
        notifyMyObserver();
    }

    /**
     * Cu ajutorul acestei metode, este adaugat un nou observator.
     */
    public void addMyObserver(MyObserver myObserver) {
        this.myObserver = myObserver;
    }

    /**
     * Cu ajutorul acestei metode, va fi notificat observatorul desemnat in metoda anterioara.
     */
    public void notifyMyObserver() {
        myObserver.update();
    }
}
