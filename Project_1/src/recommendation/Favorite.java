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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import static utils.Utils.sortDescByValue;

public class Favorite {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public Favorite(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare user din input. In momentul in care l-am gasit pe cel dat
     * in comanda, am verificat ce fel de abonament are acesta. Daca nu era premium, am afisat mesaj
     * de eroare. In caz contrar, am luat fiecare user din input (cu exceptia celui in cauza) si
     * i-am verificat lista de filme/seriale favorite. Dupa ce m-au asigurat ca video-ul curent nu
     * se ragaseste in istoricul userului dat in comanda, i-am crescut numarul de aparitii. Dupa ce
     * am parcurs toti utilizatorii, am sortat descrescator lista obtinuta cu video-uri favorite (in
     * functie de numarul de aparitii) si apoi am returnat primul video din ea. In cazul in care
     * lista obtinuta era goala, am afisat un mesaj de eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        Set<String> shows = new HashSet<>();
        LinkedHashMap<String, Integer> initialShows = new LinkedHashMap<>();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (!currentUser.getSubscriptionType().equals("PREMIUM")) {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "FavoriteRecommendation cannot be applied!");
                    return result;
                }
            }
        }

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                shows = currentUser.getHistory().keySet(); // setul cu filmele vizualizate de user
            }
        }
        // initializare hashmap cu filme ce nu au fost vazute de userul dat in comanda
        for (MovieInputData currentMovie : input.getMovies()) {
            if (!shows.contains(currentMovie.getTitle())) {
                initialShows.put(currentMovie.getTitle(), 0);
            }
        }
        // continuarea initializarii cu seriale ce nu au fost vazute de userul dat in comanda
        for (SerialInputData currentSerial : input.getSerials()) {
            if (!shows.contains(currentSerial.getTitle())) {
                initialShows.put(currentSerial.getTitle(), 0);
            }
        }
        // am realizat initializarea de mai sus pentru a asigura pastrarea ordinii video-urilor

        for (UserInputData currentUser : input.getUsers()) {
            if (!currentUser.getUsername().equals(currentCommand.getUsername())) {
                for (String currentFavorite : currentUser.getFavoriteMovies()) {
                    if (!shows.contains(currentFavorite)) {
                        initialShows.put(currentFavorite, initialShows.get(currentFavorite) + 1);
                    }
                }
            }
        }

        if (initialShows.isEmpty()) {
            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "FavoriteRecommendation cannot be applied!");
        } else {
            HashMap<String, Double> finalShows;
            finalShows = sortDescByValue(initialShows);

            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "FavoriteRecommendation result: " + finalShows.keySet().toArray()[0]);
        }

        return result;
    }
}
