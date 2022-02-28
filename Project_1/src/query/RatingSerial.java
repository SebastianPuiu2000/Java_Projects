package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.processing;
import static utils.Utils.serialRating;

public class RatingSerial {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public RatingSerial(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare serial din input si m-am asigurat ca respecta criteriile
     * din filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ,
     * i-am calculat ratingul, folosind metoda "serialRating" din clasa "Utils". Daca rezultatul era
     * diferit de 0, i-am asociat filmului curent ratingul obtinut. Pentru a afisa lista finala cu
     * serialele dorite, m-am folosit de metoda "processing" din clasa "Utils", cu ajutorul careia
     * am sortat lista construita (in functie de rating) si am extras doar primele "N" seriale din
     * ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Double> initialSerials = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalSerials;

        for (SerialInputData currentSerial : input.getSerials()) {
            if (filter(currentSerial, currentCommand)) {
                if (serialRating(currentSerial) != 0) {
                    initialSerials.put(currentSerial.getTitle(), serialRating(currentSerial));
                }
            }
        }
        finalSerials = processing(initialSerials, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalSerials.keySet());

        return result;
    }
}
