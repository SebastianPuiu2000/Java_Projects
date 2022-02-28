package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.movieRating;
import static utils.Utils.processing;

public class RatingMovie {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public RatingMovie(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare film din input si m-am asigurat ca respecta criteriile
     * din filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ,
     * i-am calculat ratingul, folosind metoda "movieRating" din clasa "Utils". Daca rezultatul era
     * diferit de 0, i-am asociat fimului curent ratingul obtinut. Pentru a afisa lista finala cu
     * filmele dorite, m-am folosit de metoda "processing" din clasa "Utils", cu ajutorul careia
     * am sortat lista construita (in functie de rating) si am extras doar primele "N" filmele din
     * ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Double> initialMovies = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalMovies;

        for (MovieInputData currentMovie : input.getMovies()) { // parte de filtrare{
            if (filter(currentMovie, currentCommand)) {
                if (movieRating(currentMovie) != 0) {
                    initialMovies.put(currentMovie.getTitle(), movieRating(currentMovie));
                }
            }
        }
        finalMovies = processing(initialMovies, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalMovies.keySet());

        return result;
    }
}
