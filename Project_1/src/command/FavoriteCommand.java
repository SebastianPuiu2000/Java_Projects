package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public class FavoriteCommand {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public FavoriteCommand(final Input input, final Writer fileWriter,
                           final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }

    /**
     * In aceasta metoda am parcurs lista de useri din input pana l-am gasit pe cel cerut in
     * comanda. Apoi, am verificat daca filmul dat in comanda se afla in istoricul sau. In caz
     * contrar, am afisat eroare. Daca se afla in istoric, atunci am verificat daca se afla in
     * lista de filme favorite. Daca nu se afla, atunci adaugam filmul dat la favorite, iar in caz
     * contrar, afisam eroare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result = new JSONObject();

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (!currentUser.getHistory().containsKey(currentCommand.getTitle())) {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "error -> " + currentCommand.getTitle() + " is not seen");
                    return result;
                }

                if (currentUser.getFavoriteMovies().contains(currentCommand.getTitle())) {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "error -> " + currentCommand.getTitle()
                                    + " is already in favourite list");
                    return result;
                }

                currentUser.getFavoriteMovies().add(currentCommand.getTitle());
                result = fileWriter.writeFile(currentCommand.getActionId(), null,
                        "success -> " + currentCommand.getTitle()
                                + " was added as favourite");

            }
        }

        return result;
    }
}
