package query;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.processing;


public class LongestSerial {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public LongestSerial(final Input input, final Writer fileWriter,
                         final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare serial din input si m-am asigurat ca respecta criteriile
     * din filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ,
     * asociam serialul curent cu durata lui totala (formata din suma duratelor fiecarui sezon).
     * Pentru a afisa lista finala cu serialele dorite, m-am folosit de metoda "processing" din
     * clasa "utils", cu ajutorul careia am sortat lista construita (in functie de durata) si am
     * extras doar primele "N" seriale din ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Integer> initialSerials = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalSerials;
        int duration;

        for (SerialInputData currentSerial : input.getSerials()) {
            if (filter(currentSerial, currentCommand)) {

                initialSerials.put(currentSerial.getTitle(), 0);
                duration = 0;

                for (Season sezon : currentSerial.getSeasons()) {
                    duration = duration + sezon.getDuration();
                }
                initialSerials.put(currentSerial.getTitle(), duration);
            }
        }
        finalSerials = processing(initialSerials, currentCommand);

        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                "Query result: " + finalSerials.keySet());

        return result;
    }
}
