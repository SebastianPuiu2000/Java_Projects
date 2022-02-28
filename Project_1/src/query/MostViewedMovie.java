package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.processing;


public class MostViewedMovie {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public MostViewedMovie(final Input in, final Writer fileWriter, final ActionInputData command) {
        this.input = in;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare film din input si m-am asigurat ca respecta criteriile din
     * filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ, i-am
     * asociat initial filmului curent 0 vizualizari. Dupa care, am luat fiecare user din input, si
     * am verificat daca filmul curent facea partea din istoricul lui. In caz afirmativ, am adaugat
     * numarul de vizualizari ale utilizatorului pentru filmul curent. Pentru a afisa lista finala
     * cu filmele dorite, m-am folosit de metoda "processing" din clasa "Utils", cu ajutorul careia
     * am sortat lista construita (in functie de numarul de vizualizari) si am extras doar primele
     * "N" filmele din ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Integer> initialMovies = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalMovies;

        for (MovieInputData currentMovie : input.getMovies()) {

            if (filter(currentMovie, currentCommand)) {
                for (UserInputData userCurent : input.getUsers()) {
                    if (userCurent.getHistory().keySet().contains(currentMovie.getTitle())) {
                        if (!initialMovies.keySet().contains(currentMovie.getTitle())) {
                            initialMovies.put(currentMovie.getTitle(), 0);
                        }
                        initialMovies.put(currentMovie.getTitle(),
                                initialMovies.get(currentMovie.getTitle())
                                        + userCurent.getHistory().get(currentMovie.getTitle()));
                    }
                }
            }

            if (initialMovies.keySet().contains(currentMovie.getTitle())) {
                if (initialMovies.get(currentMovie.getTitle()) == 0) { //nu are nicio viz prin useri
                    initialMovies.remove(currentMovie.getTitle()); // il exclud
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
