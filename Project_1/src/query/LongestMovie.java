package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.processing;

public class LongestMovie {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public LongestMovie(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare film din input si m-am asigurat ca respecta criteriile
     * din filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ,
     * asociam filmul curent cu durata lui. Pentru a afisa lista finala cu filmele dorite,
     * m-am folosit de metoda "processing" din clasa "utils", cu ajutorul careia am sortat lista
     * construita (in functie de durata) si am extras doar primele "N" filme din ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Integer> initialMovies = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalMovies;

        for (MovieInputData currentMovie : input.getMovies()) {
            if (filter(currentMovie, currentCommand)) {
                initialMovies.put(currentMovie.getTitle(), currentMovie.getDuration());
            }
        }
        finalMovies = processing(initialMovies, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalMovies.keySet());

        return result;
    }
}
