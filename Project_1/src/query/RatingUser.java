package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static utils.Utils.processing;

public class RatingUser {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public RatingUser(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare utilizator din input si i-am asociat numarul de rating-uri
     * date, doar daca era diferit de 0. Pentru a afisa lista finala cu utilizatorii doriti, m-am
     * folosit de metoda "processing" din clasa "Utils", cu ajutorul careia am sortat lista
     * construita (in functie de numarul de rating-uri) si am extras doar primii "N" utilizatori
     * din ea.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        HashMap<String, Integer> initialUsers = new HashMap<>();
        LinkedHashMap<String, Integer> finalUsers;

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getNumRatings() != 0) {
                initialUsers.put(currentUser.getUsername(), currentUser.getNumRatings());
            }
        }
        finalUsers = processing(initialUsers, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalUsers.keySet());

        return result;
    }
}
