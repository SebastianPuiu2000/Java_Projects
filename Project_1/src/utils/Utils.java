package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The class contains static methods that helps with parsing.
 * <p>
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     *
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     *
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     *
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Metoda de sortare in ordine crescatoare, mai intai in functie de valoare, si apoi in
     * functie de cheie
     */
    public static HashMap sortAsc(final HashMap initialMap) {
        List list = new LinkedList(initialMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(final Object obj1, final Object obj2) {
                return ((Comparable) ((Map.Entry) (obj1)).getValue())
                        .compareTo(((Map.Entry) (obj2)).getValue());
            }
        }.thenComparing(new Comparator() {
            public int compare(final Object obj1, final Object obj2) {
                return ((Comparable) ((Map.Entry) (obj1)).getKey())
                        .compareTo(((Map.Entry) (obj2)).getKey());
            }
        }));

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Metoda de sortare in ordine descrescatoare, mai intai in functie de valoare, si apoi in
     * functie de cheie
     */
    public static HashMap sortDesc(final HashMap initialMap) {
        List list = new LinkedList(initialMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(final Object obj1, final Object obj2) {
                return ((Comparable) ((Map.Entry) (obj2)).getValue())
                        .compareTo(((Map.Entry) (obj1)).getValue());
            }
        }.thenComparing(new Comparator() {
            public int compare(final Object obj1, final Object obj2) {
                return ((Comparable) ((Map.Entry) (obj2)).getKey())
                        .compareTo(((Map.Entry) (obj1)).getKey());
            }
        }));

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Metoda de sortare in ordine descrescatoare, in functie de valoare
     */
    public static HashMap sortDescByValue(final HashMap initialMap) {
        List list = new LinkedList(initialMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(final Object obj1, final Object obj2) {
                return ((Comparable) ((Map.Entry) (obj2)).getValue())
                        .compareTo(((Map.Entry) (obj1)).getValue());
            }
        });

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Metoda de filtrare a unui film/serial in functie de filtrele din comanda data.
     * Daca acesta indeplineste toate criteriile, metoda intoarce "true", iar in caz contrar "false"
     */
    public static boolean filter(final ShowInput show, final ActionInputData command) {

        if (command.getFilters().get(0).contains(null)
                && command.getFilters().get(1).contains(null)) {
            return true;
        }

        if (!command.getFilters().get(0).contains(null)
                && command.getFilters().get(1).contains(null)) {
            if (command.getFilters().get(0)
                    .contains(Integer.toString(show.getYear()))) {
                return true;
            }
        }

        if (command.getFilters().get(0).contains(null)
                && !command.getFilters().get(1).contains(null)) {
            if (show.getGenres().containsAll(command.getFilters()
                    .get(1))) {
                return true;
            }
        }

        if (!command.getFilters().get(0).contains(null)
                && !command.getFilters().get(1).contains(null)) {
            if (command.getFilters().get(0)
                    .contains(Integer.toString(show.getYear()))) {
                if (show.getGenres().containsAll(command.getFilters().get(1))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Metoda prin care se obtine o lista sortata dupa criteriul dat in comanda si contine maxim "N"
     * elemente, acest numar fiind specificat tot in comanda
     */
    public static LinkedHashMap processing(final HashMap initMap, final ActionInputData command) {
        LinkedHashMap finalMap = new LinkedHashMap();
        HashMap auxiliarMap = new HashMap();
        int contor = 0;

        if (command.getSortType().equals("asc")) {
            auxiliarMap = sortAsc(initMap);
        }
        if (command.getSortType().equals("desc")) {
            auxiliarMap = sortDesc(initMap);
        }

        for (Object key : auxiliarMap.keySet()) {
            if (contor < command.getNumber()) {
                finalMap.put(key, auxiliarMap.get(key));
                contor++;
            }
        }
        return finalMap;

    }

    /**
     * Metoda prin care se calculeaza rating-ul unui film
     */
    public static double movieRating(final MovieInputData movie) {
        double ratingMovie = 0.0;
        double suma = 0.0;
        double nr = 0.0;
        for (double ratingCurent : movie.getRatings()) {
            suma = suma + ratingCurent;
            nr = nr + 1.0;
        }
        if (nr != 0.0) {
            ratingMovie = suma / nr;
        }

        return ratingMovie;
    }

    /**
     * Metoda prin care se calculeaza rating-ul unui serial (in functie de mediile sezoanelor)
     */
    public static double serialRating(final SerialInputData serial) {
        double ratingSerial = 0.0;
        double ratingSezon, suma, nr;
        for (Season seasonCurent : serial.getSeasons()) {
            suma = 0.0;
            nr = 0.0;
            ratingSezon = 0.0;
            for (double ratingCurent : seasonCurent.getRatings()) {
                suma = suma + ratingCurent;
                nr = nr + 1.0;
            }
            if (nr != 0.0) {
                ratingSezon = suma / nr;
            }
            ratingSerial = ratingSerial + ratingSezon;

        }
        if (ratingSerial != 0.0) {
            ratingSerial = ratingSerial / serial.getNumberSeason();
        }

        return ratingSerial;
    }
}
