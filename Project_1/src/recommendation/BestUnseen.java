package recommendation;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static utils.Utils.movieRating;
import static utils.Utils.serialRating;
import static utils.Utils.sortDescByValue;

public class BestUnseen {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public BestUnseen(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare user din input. Dupa ce l-am gasit pe cel din comanda,
     * am luat fiecare film si serial din input si m-am asigurat ca nu fac parte din istoricul
     * utilizatorului in cauza. Pentru fiecare video ce respecta aceasta conditie, i-am asociat
     * ratingul lui, calculat prin metodele "movieRating" sau "serialRating", ambele facand parte
     * din clasa "Utils". Apoi, am ordonat descrescator (in functie de rating-uri) lista obtinuta,
     * si am returnat primul video din ea. In cazul in care lista obtinuta era goala, am afisat un
     * mesaj de eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Double> initialShows = new LinkedHashMap<>();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {

                for (MovieInputData currentMovie : input.getMovies()) {
                    if (!currentUser.getHistory().keySet().contains(currentMovie.getTitle())) {
                        initialShows.put(currentMovie.getTitle(), movieRating(currentMovie));
                    }
                }

                for (SerialInputData currentSerial : input.getSerials()) {
                    if (!currentUser.getHistory().keySet().contains(currentSerial.getTitle())) {
                        initialShows.put(currentSerial.getTitle(), serialRating(currentSerial));
                    }
                }
            }
        }

        if (initialShows.isEmpty()) {
            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "BestRatedUnseenRecommendation cannot be applied!");
        } else {
            HashMap<String, Double> finalShows;
            finalShows = sortDescByValue(initialShows);

            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "BestRatedUnseenRecommendation result: " + finalShows.keySet().toArray()[0]);
        }

        return result;
    }
}
