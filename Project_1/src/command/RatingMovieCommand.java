package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public class RatingMovieCommand {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public RatingMovieCommand(final Input input, final Writer fileWriter,
                              final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }

    /**
     * In aceasta metoda am parcurs lista de useri din input pana l-am gasit pe cel cerut in
     * comanda. Apoi, am verificat daca filmul dat in comanda se afla in istoricul sau. In caz
     * contrar, am afisat eroare. Daca se afla in istoric, atunci am verificat daca se afla in
     * lista de filme ce au primit rating de la user-ul in cauza. In caz afirmativ, am afisat
     * eroare, iar in caz contrar, am adaugat noul rating in lista cu ratinguri primite de filmul
     * in cauza. (am si adaugat filmul in lista user-ului de filme ce au primit rating de la el. De
     * asemenea, am si actualizat numarul de rating-uri date in total de user)
     */
    public final JSONObject result() throws IOException {
        JSONObject result = new JSONObject();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (currentUser.getHistory().containsKey(currentCommand.getTitle())) {

                    if (currentUser.getMovieRating().containsKey(currentCommand.getTitle())) {
                        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                "error -> " + currentCommand.getTitle()
                                        + " has been already rated");
                        return result;
                    }
                    currentUser.getMovieRating().put(currentCommand.getTitle(),
                            currentCommand.getGrade());

                    for (MovieInputData currentMovie : input.getMovies()) {
                        if (currentMovie.getTitle().equals(currentCommand.getTitle())) {
                            currentMovie.getRatings().add(currentCommand.getGrade());
                        }
                    }

                    currentUser.addRating();
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "success -> " + currentCommand.getTitle() + " was rated with "
                                    + currentCommand.getGrade()
                                    + " by " + currentUser.getUsername());
                } else {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "error -> " + currentCommand.getTitle() + " is not seen");
                }
            }
        }

        return result;
    }
}
