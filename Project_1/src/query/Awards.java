package query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import static utils.Utils.processing;
import static utils.Utils.stringToAwards;


public class Awards {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;
    public static final int NUMBER = 3;

    public Awards(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am transformat mai intai lista de premii din filtru intr-un set de tipul
     * ActorsAwards. Dupa care, am verificat pentru fiecare actor din input daca are tot setul de
     * premii cerute. In caz afirmativ, calculam cate premii are in total si ii asociam rezultatul
     * actorului curent. Pentru a afisa lista finala cu actorii ceruti, m-am folosit de metoda
     * "processing" din clasa "utils", cu ajutorul careia am sortat lista construita si am extras
     * doar primii "N" actori din ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        Set<ActorsAwards> curentAwards = new HashSet();
        LinkedHashMap<String, Integer> initialActors = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalActors;
        int totalAwards;

        for (String award : currentCommand.getFilters().get(NUMBER)) {
            curentAwards.add(stringToAwards(award)); // am transformat stringurile intr un enum
        }

        for (ActorInputData currentActor : input.getActors()) {
            if (currentActor.getAwards().keySet().containsAll(curentAwards)) {
                totalAwards = 0;

                for (int numAwards : currentActor.getAwards().values()) {
                    totalAwards = totalAwards + numAwards;
                }
                initialActors.put(currentActor.getName(), totalAwards);
            }
        }

        finalActors = processing(initialActors, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalActors.keySet());

        return result;
    }
}
