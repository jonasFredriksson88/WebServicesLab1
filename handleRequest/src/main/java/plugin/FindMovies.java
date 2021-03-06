package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.HttpStatus;
import utils.Request;
import utils.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Url("/movies")
public class FindMovies implements Spi {

    @Override
    public Response handleRequest(Request request) {
        System.out.println(request.urlParams.get(getKey(request)));
        Response response = new Response();

        if (request.type == HTTPType.POST) {
            return HttpStatus.status400();
        }

        response.type = request.type;
        List<Movie> movies;
        movies = getMovies(request);

        Gson gson = new Gson();
        if (!movies.isEmpty()) {
            String jsonStr = gson.toJson(movies);
            response.body = jsonStr.getBytes(StandardCharsets.UTF_8);
            response.contentType = "application/json";
        } else {
            response.body = "Could not find movie\r\n".getBytes(StandardCharsets.UTF_8);
            response.contentType = "text/plain";
        }

        response.status = HttpStatus.status200();

        return response;
    }

    private List<Movie> getMovies(Request request) throws NumberFormatException {
        String key = getKey(request);

        return switch (key) {
            case "id" -> Functions.findMovieById(Long.valueOf(request.urlParams.get("id")));
            case "title" -> Functions.findMoviesByTitle(request.urlParams.get("title"));
            case "director" -> Functions.findMoviesByDirector(request.urlParams.get("director"));
            case "length" -> Functions.findMoviesByLength(Integer.parseInt(request.urlParams.get("length")));
            case "releaseYear" -> Functions.findMoviesByYear(Integer.parseInt(request.urlParams.get("releaseYear")));
            case "" -> Functions.getAllMovies();
            default -> new ArrayList<>();
        };
    }

    private String getKey(Request request) {
        if (request.urlParams.containsKey("id")) return "id";
        else if (request.urlParams.containsKey("director")) return "director";
        else if (request.urlParams.containsKey("length")) return "length";
        else if (request.urlParams.containsKey("releaseYear")) return "releaseYear";
        else if (request.urlParams.containsKey("title")) return "title";
        else return "";
    }
}
