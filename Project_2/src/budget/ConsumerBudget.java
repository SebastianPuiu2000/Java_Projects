package budget;

import person.Consumer;
import person.Distributor;
import reader.Input;

public final class ConsumerBudget {

    public static final double COST = 1.2;

    private ConsumerBudget() {
    }

    /**
     * In cadrul acestei metode, mai intai le adaug tuturor consumatorilor salariile din acea luna.
     * Apoi, verific daca consumatorul curent si-a folosit a doua sansa de a-si plati facturile.
     * In cazul in care nu si-a folosit pana acum a doua sansa, inseamna ca nu are nicio datorie,
     * deci prin urmare pot trece la urmatoare etapa. Daca are bani pentru a-si putea plati
     * factura curenta, ii scad suma respectiva din buget, iar metoda se incheie. In caz contrar,
     * ii voi retine in campul "debt" datoria pe acea luna si inseamna ca si-a pierdut dreptul la
     * cea de-a doua sansa.
     * Daca in luna anterioara consumatorul si-a pierdut dreptul la a doua sansa, iar acum nu are
     * bani sa plateasca datoriile, este declarat falimentar. In cazul in care are suficienti bani,
     * o sa i se scada din buget suma respectiva, va primi din nou dreptul la o a doua sansa si
     * ii va da datoria distribuitorului in cauza.
     */
    public static void update(final Input input) {

        for (Consumer currentConsumer : input.getConsumers()) {
            if (!currentConsumer.isBankrupt()) {
                currentConsumer.setBudget(currentConsumer.getBudget()
                        + currentConsumer.getMonthlyIncome());
            }
        }

        for (Consumer currentConsumer : input.getConsumers()) {
            if (currentConsumer.isSecondChance()) {
                if (currentConsumer.getBudget() - currentConsumer.getBill() < 0) {
                    currentConsumer.setSecondChance(false);
                    currentConsumer.setDebt(currentConsumer.getBill());
                } else {
                    currentConsumer.setBudget(
                            (int) (currentConsumer.getBudget() - currentConsumer.getBill()));
                }

            } else {
                if (currentConsumer.getBudget()
                        - (Math.round(Math.floor(COST * currentConsumer.getDebt()))
                        + currentConsumer.getBill()) < 0) {
                    currentConsumer.setBankrupt(true);
                } else {

                    currentConsumer.setBudget((int) (currentConsumer.getBudget()
                            - (Math.round(Math.floor(COST * currentConsumer.getDebt()))
                            + currentConsumer.getBill())));

                    for (Distributor currentDistributor : input.getDistributors()) {
                        if (currentDistributor.getId() == currentConsumer.getDistributorId()) {
                            currentDistributor.setBudget((int) (currentDistributor.getBudget()
                                    + Math.round(Math.floor(COST * currentConsumer.getDebt()))));

                        }
                    }
                    currentConsumer.setSecondChance(true);
                }

            }
        }
    }
}
