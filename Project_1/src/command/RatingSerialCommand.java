package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RatingSerialCommand {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public RatingSerialCommand(final Input input, final Writer fileWriter,
                               final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda am parcurs lista de useri din input pana l-am gasit pe cel cerut in
     * comanda. Apoi, am verificat daca serialul dat in comanda se afla in istoricul sau. In caz
     * contrar, am afisat eroare. Daca se afla in istoric, atunci am verificat daca sezonul dat in
     * comanda se afla in lista de sezoane (a serialului dat) ce au primit rating de la user-ul in
     * cauza. In caz afirmativ, am afisat eroare, iar in caz contrar, am adaugat noul rating in
     * lista cu ratinguri primite de sezonul serialului in cauza. (Am si adaugat sezonul in lista
     * user-ului de sezoane din serialul curent ce au primit rating de la el. De asemenea, am si
     * actualizat numarul de rating-uri date in total de user)
     */
    public final JSONObject result() throws IOException {
        JSONObject result = new JSONObject();
        ArrayList<Integer> ratedSeasons;

        for (UserInputData currentUser : input.getUsers()) {
            if (currentUser.getUsername().equals(currentCommand.getUsername())) {
                if (currentUser.getHistory().containsKey(currentCommand.getTitle())) {
                    ratedSeasons = currentUser.getShowRating().get(currentCommand.getTitle());

                    if (ratedSeasons != null) {
                        if (ratedSeasons.contains(currentCommand.getSeasonNumber())) {
                            result = fileWriter.writeFile(currentCommand.getActionId(), null,
                                    "error -> " + currentCommand.getTitle()
                                            + " has been already rated");
                            return result;
                        }
                    }
                    if (ratedSeasons == null) {
                        ratedSeasons = new ArrayList<>();
                    }
                    ratedSeasons.add(currentCommand.getSeasonNumber());
                    currentUser.getShowRating().put(currentCommand.getTitle(), ratedSeasons);

                    for (SerialInputData currentSerial : input.getSerials()) {
                        if (currentSerial.getTitle().equals(currentCommand.getTitle())) {
                            currentSerial.getSeasons().get(currentCommand.getSeasonNumber() - 1)
                                    .getRatings().add(currentCommand.getGrade());
                        }
                    }

                    currentUser.addRating();
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "success -> " + currentCommand.getTitle() + " was rated with "
                                    + currentCommand.getGrade() + " by "
                                    + currentUser.getUsername());
                } else {
                    result = fileWriter.writeFile(currentCommand.getActionId(), null,
                            "error -> " + currentCommand.getTitle() + " is not seen");
                }


            }
        }
        return result;
    }
}
