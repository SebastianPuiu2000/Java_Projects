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

import static utils.Utils.movieRating;
import static utils.Utils.serialRating;
import static utils.Utils.sortAsc;

public class Search {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public Search(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare user din input. In momentul in care l-am gasit pe cel dat
     * in comanda, am verificat ce fel de abonament are acesta. Daca nu era premium, am afisat mesaj
     * de eroare. In caz contrar, am luat fiecare film din input, iar in cazul in care avea genurile
     * cerute in comanda si nici nu a fost vazut de user-ul in cauza, i-am calculat rating-ul
     * folosind metoda "movieRating" din clasa "Utils" si l-am asociat cu rezultatul obtinut. Am
     * procedat in acelasi fel si in cazul serialelor (in acest caz am folosit metoda "serialRating"
     * din clasa "Utils" pentru a determina rating-ul corespunzator). Apoi, am sortat crescator
     * lista obtinuta (in functie de rating-uri) prin intermediul metodei "sortAsc" ce apartine tot
     * clasei "Utils". In cazul in care lista obtinuta era goala, am afisat un mesaj de eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        HashMap<String, Double> initialShows = new HashMap<>();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (!currentUser.getSubscriptionType().equals("PREMIUM")) {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "SearchRecommendation cannot be applied!");
                    return result;
                }
            }
        }

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {

                    for (MovieInputData currentMovie : input.getMovies()) {
                        if (!currentUser.getHistory().keySet().contains(currentMovie.getTitle())) {
                            if (currentMovie.getGenres().contains(currentCommand.getGenre())) {
                                initialShows.put(currentMovie.getTitle(),
                                        movieRating(currentMovie));
                            }
                        }
                    }

                    for (SerialInputData currentSerial : input.getSerials()) {
                        if (!currentUser.getHistory().keySet().contains(currentSerial.getTitle())) {
                            if (currentSerial.getGenres().contains(currentCommand.getGenre())) {
                                initialShows.put(currentSerial.getTitle(),
                                        serialRating(currentSerial));

                            }
                        }
                    }
            }
        }

        if (initialShows.isEmpty()) {
            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "SearchRecommendation cannot be applied!");
            return result;
        } else {
            HashMap<String, Double> finalShows;
            finalShows = sortAsc(initialShows);

            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "SearchRecommendation result: " + finalShows.keySet());
            return result;
        }

    }
}
