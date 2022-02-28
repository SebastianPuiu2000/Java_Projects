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

public class Popular {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public Popular(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare user din input. In momentul in care l-am gasit pe cel dat
     * in comanda, am verificat ce fel de abonament are acesta. Daca nu era premium, am afisat mesaj
     * de eroare. In caz contrar, am parcurs toate filmele si serialele care nu existau in istoricul
     * user-ului in cauza. Daca era respectat acest criteriu, am luat toate genurile pe care le
     * continea acesta si le-am crescut la toate numarul de aparitii. Dupa ce am terminat de parcurs
     * toate video-urile, am sortat descrescator lista de genuri (in functie de numarul de aparitii)
     * Apoi, am retunat primul video din input care continea cel mai vizualizat (primul) gen din
     * lista obtinuta (si nu facea parte din istoricul user-ului in cauza). In cazul in care lista
     * de genuri obtinuta este goala, afisez un mesaj de eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result = new JSONObject();
        Set<String> shows = new HashSet<>();
        LinkedHashMap<String, Integer> initialGenres = new LinkedHashMap<>();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (!currentUser.getSubscriptionType()
                        .equals("PREMIUM")) {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "PopularRecommendation cannot be applied!");
                    return result;
                }
            }
        }

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                shows = currentUser.getHistory().keySet();
            }
        }

        for (UserInputData currentUser : input.getUsers()) {
            if (!currentUser.getUsername().equals(currentCommand.getUsername())) {

                for (MovieInputData currentMovie : input.getMovies()) {
                    if (currentUser.getHistory().keySet().contains(currentMovie.getTitle())) {
                        if (!shows.contains(currentMovie.getTitle())) {
                            for (String gen : currentMovie.getGenres()) {
                                if (!initialGenres.keySet().contains(gen)) {
                                    initialGenres.put(gen, 0);
                                }
                            initialGenres.put(gen, initialGenres.get(gen)
                                    + currentUser.getHistory().get(currentMovie.getTitle()));
                            }
                        }
                    }
                }

                for (SerialInputData currentSerial : input.getSerials()) {
                    if (currentUser.getHistory().keySet().contains(currentSerial.getTitle())) {
                        if (!shows.contains(currentSerial.getTitle())) {
                            for (String gen : currentSerial.getGenres()) {
                                if (!initialGenres.keySet().contains(gen)) {
                                    initialGenres.put(gen, 0);
                                }
                                initialGenres.put(gen, initialGenres.get(gen)
                                        + currentUser.getHistory().get(currentSerial.getTitle()));
                            }
                        }
                    }
                }
            }
        }

        if (initialGenres.isEmpty()) {
            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                    "PopularRecommendation cannot be applied!");
            return result;
        } else {
            HashMap<String, Double> finalGenres;
            finalGenres = sortDescByValue(initialGenres);

            for (MovieInputData currentMovie : input.getMovies()) {
                if (currentMovie.getGenres().contains(finalGenres.keySet().toArray()[0])) {
                    if (!shows.contains(currentMovie.getTitle())) {
                        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                "PopularRecommendation result: "
                                        + currentMovie.getTitle());
                        return result;
                    }
                }
            }

            for (SerialInputData currentSerial : input.getSerials()) {
                if (currentSerial.getGenres().contains(finalGenres.keySet().toArray()[0])) {
                    if (!shows.contains(currentSerial.getTitle())) {
                        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                "PopularRecommendation result: "
                                        + currentSerial.getTitle());
                        return result;
                    }
                }
            }
        }
        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                "PopularRecommendation cannot be applied!");
        return result;
    }
}
