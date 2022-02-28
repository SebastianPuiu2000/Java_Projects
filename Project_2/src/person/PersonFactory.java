package person;

import org.json.simple.JSONObject;

public final class PersonFactory {

    private static PersonFactory instance;

    private PersonFactory() {
    }

    /**
     * M-am folosit de design pattern-ul Singleton
     */
    public static PersonFactory getInstance() {
        if (instance == null) {
            instance = new PersonFactory();
        }
        return instance;
    }

    /**
     * In functie de tipul de persoana ceruta, aceasta metoda returneaza fie un Distributor,
     * fie un Consumer, fie un Producer, iar pentru a fii cat mai generic, am vrut sa-mi returneze
     * un obiect de tip Person, ce le include pe fiecare din cele de mai sus.
     */
    public Person getPerson(final String personType, final JSONObject person) {

        if (personType != null) {
            if (personType.equals("consumer")) {

                if (person.get("id") != null) {
                    return new Consumer(Integer.parseInt(person.get("id").toString()),
                            Integer.parseInt(person.get("initialBudget").toString()),
                            Integer.parseInt(person.get("monthlyIncome").toString()));
                }
            }

            if (personType.equals("distributor")) {

                if (person.get("id") != null) {
                    return new Distributor(Integer.parseInt(person.get("id").toString()),
                            Integer.parseInt(person.get("contractLength").toString()),
                            Integer.parseInt(person.get("initialBudget").toString()),
                            Integer.parseInt(person.get("initialInfrastructureCost").toString()),
                            Integer.parseInt(person.get("energyNeededKW").toString()),
                            person.get("producerStrategy").toString());
                }
            }

            if (personType.equals("producer")) {

                if (person.get("id") != null) {
                    return new Producer(Integer.parseInt(person.get("id").toString()),
                            person.get("energyType").toString(),
                            Integer.parseInt(person.get("maxDistributors").toString()),
                            Double.parseDouble(person.get("priceKW").toString()),
                            Integer.parseInt(person.get("energyPerDistributor").toString()));
                }
            }
        }

        return null;
    }

}
