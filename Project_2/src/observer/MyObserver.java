package observer;

public abstract class MyObserver {
    protected ProducerImpact producerImpact;

    /**
     * Aici se gaseste metoda de update a observatorului, atunci cand acesta este notificat.
     */
    public abstract void update();
}
