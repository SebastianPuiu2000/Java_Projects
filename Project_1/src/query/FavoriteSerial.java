package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.filter;
import static utils.Utils.processing;

public class FavoriteSerial {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public FavoriteSerial(final Input in, final Writer fileWriter, final ActionInputData command) {
        this.input = in;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare serial din input si m-am asigurat ca respecta criteriile
     * din filtrul dat in comanda, folosind metoda "filter" din clasa "Utils". In caz afirmativ,
     * i-am asociat initial serialului curent 0 aparitii (ca si favorit). Dupa care, am luat fiecare
     * user din input, si am verificat daca serialul curent facea partea din lista lui de favorite.
     * In caz afirmativ, am incrementat numarul de aparitii ca si favorit al serialului curent.
     * Pentru a afisa lista finala cu serialele dorite, m-am folosit de metoda "processing" din
     * clasa "Utils", cu ajutorul careia am sortat lista construita (in functie de numarul de
     * aparitii la favorite) si am extras doar primele "N" serialele din ea.
     */
    public JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Integer> initialSerials = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalSerials;

        for (SerialInputData currentSerial : input.getSerials()) {
            if (filter(currentSerial, currentCommand)) {
                for (UserInputData userCurent : input.getUsers()) {

                    if (!initialSerials.keySet().contains(currentSerial.getTitle())) {
                        initialSerials.put(currentSerial.getTitle(), 0);
                    }

                    if (userCurent.getFavoriteMovies().contains(currentSerial.getTitle())) {
                        initialSerials.put(currentSerial.getTitle(),
                                initialSerials.get(currentSerial.getTitle()) + 1);
                    }
                }
            }

            if (initialSerials.keySet().contains(currentSerial.getTitle())) {
                if (initialSerials.get(currentSerial.getTitle()) == 0) { //nuarenicio viz prin useri
                    initialSerials.remove(currentSerial.getTitle()); // il exclud
                }
            }
        }

        finalSerials = processing(initialSerials, currentCommand);

        result = fileWriter
                .writeFile(currentCommand.getActionId(), null,
                        "Query result: " + finalSerials.keySet());

        return result;
    }
}
