package query;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

import static utils.Utils.movieRating;
import static utils.Utils.processing;
import static utils.Utils.serialRating;

public class Average {

    private final Input input;
    private final Writer fileWriter;
    private final ActionInputData currentCommand;

    public Average(final Input input, final Writer fileWriter, final ActionInputData command) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.currentCommand = command;
    }
    /**
     * In aceasta metoda, am luat fiecare actor din input, si am gasit toate filmele si serialele
     * in care a jucat acesta. De fiecare data cand gaseam un now show ce respecta acest criteriu,
     * ii aflam ratingul si il adaugam la ratingul total al actorului. Daca ce am terminat de
     * parcurs toate filmele/serialele din input, am realizat media aritmetica a tuturor
     * ratingurilor calculate si i-am asociat-o actorului in cauza. In continuare, nu am luat in
     * calcul actorii ce aveau ratingul egal cu 0. Pentru a afisa lista finala cu actorii ceruti,
     * m-am folosit de metoda "processing" din clasa "utils", cu ajutorul careia am sortat lista
     * construita si am extras doar primii "N" actori din ea. Mentionez ca si ratingul l-am calculat
     * folosind metodele "movieRating" si "serialRating", implementate tot in clasa "Utils".
     *
     */
    public final JSONObject result() throws IOException {
        JSONObject result;
        LinkedHashMap<String, Double> initialActors = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> finalActors;
        double nr, actorRating;

        for (ActorInputData currentActor : input.getActors()) {
            actorRating = 0.0;
            nr = 0.0;

            for (MovieInputData currentMovie : input.getMovies()) {
                if (currentActor.getFilmography().contains(currentMovie.getTitle())) {
                    actorRating = actorRating + movieRating(currentMovie);
                    if (movieRating(currentMovie) != 0) {
                        nr = nr + 1.0;
                    }
                }
            }

            for (SerialInputData currentSerial : input.getSerials()) {
                if (currentActor.getFilmography().contains(currentSerial.getTitle())) {
                    actorRating = actorRating + serialRating(currentSerial);
                    if (serialRating(currentSerial) != 0) {
                        nr = nr + 1.0;
                    }
                }
            }
            if (nr != 0) {
                initialActors.put(currentActor.getName(), actorRating / nr);
            }
        }
        finalActors = processing(initialActors, currentCommand);

        result = fileWriter.writeFile(currentCommand.getActionId(), null,
                "Query result: " + finalActors.keySet());

        return result;
    }
}
