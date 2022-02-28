package main;

import command.FavoriteCommand;
import command.RatingMovieCommand;
import command.RatingSerialCommand;
import command.ViewCommand;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import org.json.simple.JSONArray;
import query.Average;
import query.Awards;
import query.FilterDescription;
import query.LongestMovie;
import query.LongestSerial;
import query.MostViewedMovie;
import query.MostViewedSerial;
import query.FavoriteMovie;
import query.FavoriteSerial;
import query.RatingMovie;
import query.RatingSerial;
import query.RatingUser;
import recommendation.BestUnseen;
import recommendation.Popular;
import recommendation.Favorite;
import recommendation.Search;
import recommendation.Standard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        for (ActionInputData command : input.getCommands()) {

            if (command.getActionType().equals("command")) {
                if (command.getType().equals("favorite")) {
                    FavoriteCommand favoriteCommand
                            = new FavoriteCommand(input, fileWriter, command);
                    arrayResult.add(favoriteCommand.result());
                }

                if (command.getType().equals("view")) {
                    ViewCommand viewCommand = new ViewCommand(input, fileWriter, command);
                    arrayResult.add(viewCommand.result());
                }

                if (command.getType().equals("rating")
                        && command.getSeasonNumber() == 0) {
                    RatingMovieCommand ratingMovieCommand
                            = new RatingMovieCommand(input, fileWriter, command);
                    arrayResult.add(ratingMovieCommand.result());
                }

                if (command.getType().equals("rating")
                        && command.getSeasonNumber() != 0) {
                    RatingSerialCommand ratingSerialCommand
                            = new RatingSerialCommand(input, fileWriter, command);
                    arrayResult.add(ratingSerialCommand.result());
                }
            }
            if (command.getActionType().equals("query")) {
                if (command.getCriteria().equals("average")) {
                    Average average = new Average(input, fileWriter, command);
                    arrayResult.add(average.result());
                }

                if (command.getCriteria().equals("awards")) {
                    Awards awards = new Awards(input, fileWriter, command);
                    arrayResult.add(awards.result());
                }

                if (command.getCriteria().equals("filter_description")) {
                    FilterDescription filterDescription
                            = new FilterDescription(input, fileWriter, command);
                    arrayResult.add(filterDescription.result());
                }

                if (command.getCriteria().equals("favorite")
                        && command.getObjectType().equals("movies")) {
                    FavoriteMovie favoriteMovie
                            = new FavoriteMovie(input, fileWriter, command);
                    arrayResult.add(favoriteMovie.result());
                }

                if (command.getCriteria().equals("favorite")
                        && command.getObjectType().equals("shows")) {
                    FavoriteSerial favoriteSerial
                            = new FavoriteSerial(input, fileWriter, command);
                    arrayResult.add(favoriteSerial.result());
                }

                if (command.getCriteria().equals("longest")
                        && command.getObjectType().equals("movies")) {
                    LongestMovie longestMovie = new LongestMovie(input, fileWriter, command);
                    arrayResult.add(longestMovie.result());
                }

                if (command.getCriteria().equals("longest")
                        && command.getObjectType().equals("shows")) {
                    LongestSerial longestSerial = new LongestSerial(input, fileWriter, command);
                    arrayResult.add(longestSerial.result());
                }

                if (command.getCriteria().equals("most_viewed")
                        && command.getObjectType().equals("movies")) {
                    MostViewedMovie mostViewedMovie
                            = new MostViewedMovie(input, fileWriter, command);
                    arrayResult.add(mostViewedMovie.result());
                }

                if (command.getCriteria().equals("most_viewed")
                        && command.getObjectType().equals("shows")) {
                    MostViewedSerial mostViewedSerial
                            = new MostViewedSerial(input, fileWriter, command);
                    arrayResult.add(mostViewedSerial.result());
                }

                if (command.getCriteria().equals("ratings")
                        && command.getObjectType().equals("movies")) {
                    RatingMovie ratingMovie = new RatingMovie(input, fileWriter, command);
                    arrayResult.add(ratingMovie.result());
                }

                if (command.getCriteria().equals("ratings")
                        && command.getObjectType().equals("shows")) {
                    RatingSerial ratingSerial = new RatingSerial(input, fileWriter, command);
                    arrayResult.add(ratingSerial.result());
                }

                if (command.getCriteria().equals("num_ratings")
                        && command.getObjectType().equals("users")) {
                    RatingUser ratingUser = new RatingUser(input, fileWriter, command);
                    arrayResult.add(ratingUser.result());
                }
            }

            if (command.getActionType().equals("recommendation")) {
                if (command.getType().equals("standard")) {
                    Standard standard = new Standard(input, fileWriter, command);
                    arrayResult.add(standard.result());
                }

                if (command.getType().equals("search")) {
                    Search search = new Search(input, fileWriter, command);
                    arrayResult.add(search.result());
                }

                if (command.getType().equals("best_unseen")) {
                    BestUnseen bestUnseen = new BestUnseen(input, fileWriter, command);
                    arrayResult.add(bestUnseen.result());
                }

                if (command.getType().equals("favorite")) {
                    Favorite favorite = new Favorite(input, fileWriter, command);
                    arrayResult.add(favorite.result());
                }

                if (command.getType().equals("popular")) {
                    Popular popular = new Popular(input, fileWriter, command);
                    arrayResult.add(popular.result());
                }

            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
