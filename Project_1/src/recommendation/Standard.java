package recommendation;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Standard {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public Standard(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare user din input. Dupa ce l-am gasit pe cel din comanda,
     * am luat fiecare film din input pana cand il gaseam pe primul ce nu facea parte din istoricul
     * useru-lui in cauza. Daca nu era gasit niciun film, repetam acelasi proces, dar in cazul
     * acesta pentru seriale. La primul video gasit, afisam rezultatul recomandarii, iar in cazul
     * in care nu se gasea niciun video din input care sa nu faca parte din istoricului useru-lui
     * in cauza, afisam mesajul de eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {

                for (MovieInputData currentMovie : input.getMovies()) {
                    if (!currentUser.getHistory().keySet().contains(currentMovie.getTitle())) {
                        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                "StandardRecommendation result: " + currentMovie.getTitle());
                        return result;
                    }
                }

                for (SerialInputData currentSerial : input.getSerials()) {
                    if (!currentUser.getHistory().keySet().contains(currentSerial.getTitle())) {
                        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                "StandardRecommendation result: " + currentSerial.getTitle());
                        return result;
                    }
                }
            }
        }
        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                "StandardRecommendation cannot be applied!");

        return result;
    }
}
