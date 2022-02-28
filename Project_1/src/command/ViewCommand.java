package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ViewCommand {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public ViewCommand(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda am parcurs lista de useri din input pana l-am gasit pe cel cerut in
     * comanda. Apoi, am verificat daca filmul dat in comanda se afla in istoricul sau. In caz
     * afirmativ, am adaugat o vizualizare in plus la el, iar in caz negativ, l-am initializat
     * in istoric cu prima vizualizare.
     */
    public final JSONObject result() throws IOException {
        JSONObject result = new JSONObject();
        Integer number;

        for (UserInputData currentUser : input.getUsers()) {

            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (!currentUser.getHistory().containsKey(currentCommand.getTitle())) {
                    currentUser.getHistory()
                            .put(currentCommand.getTitle(), 1);
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "success -> " + currentCommand.getTitle()
                                    + " was viewed with total views of " + 1);
                } else {
                    number = currentUser.getHistory().get(currentCommand.getTitle());
                    number++;
                    currentUser.getHistory().put(currentCommand.getTitle(), number);
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "success -> " + currentCommand.getTitle()
                                    + " was viewed with total views of " + number);
                }
            }
        }
        return result;
    }

}
