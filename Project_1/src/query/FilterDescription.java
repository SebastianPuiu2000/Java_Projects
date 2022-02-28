package query;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class FilterDescription {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public FilterDescription(final Input in, final Writer fWriter, final ActionInputData command) {
        this.input = in;
        this.fileWriter = fWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare actor din input si i-am prelucrat descrierea in urmatorul
     * fel: am inlocuit orice caracter ce nu facea parte din alfabet cu " " (un spatiu gol), pentru
     * a putea separa toate cuvintele dupa acest criteriu. Apoi, am verificat daca descrierea are
     * in componenta ei toate cuvintele date in filtru (acestea fiind precedate si urmate de cate
     * un spatiu, pentru a nu lua in considerea si cazul in care un cuvant mai mare contine unul mai
     * mic). Mentionez ca am aplicat metoda "toLowerCase" asupra tuturor, pentru a ma asigura ca nu
     * va face diferenta intre literele mari si cele mici (deci m-am folosit de case insensitive).
     * Doar daca descrierea continea toate cuvintele din filtru, atunci adaugam actorul curent in
     * lista. La final, am sortat lista de actori gasiti si apoi am afisat-o.
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        List<String> actors = new ArrayList<>();
        List<String> wantedWords;
        String description;
        int ok;

        for (ActorInputData currentActor : input.getActors()) {
                ok = 1;
                description = currentActor.getCareerDescription();
                wantedWords = currentCommand.getFilters().get(2);

                description = description.replaceAll("[^A-Za-z]", " ");

                for (String word : wantedWords) {
                    if (!description.toLowerCase().contains(" " + word.toLowerCase() + " ")) {
                        ok = 0;
                        break;
                    }
                }
                if (ok == 1) {
                    actors.add(currentActor.getName());
                }
        }

        if (currentCommand.getSortType().equals("asc")) {
            Collections.sort(actors);
        }
        if (currentCommand.getSortType().equals("desc")) {
            Collections.sort(actors, Collections.reverseOrder());
        }

        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                        "Query result: " + actors);

        return result;
    }
}
