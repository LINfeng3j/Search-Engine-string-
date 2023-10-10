import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class SearchHandler implements URLHandler {
    // The list to store strings
    List<String> stringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String queryString = url.getQuery();
            String[] parameters = queryString.split("=");
            if (parameters.length == 2 && parameters[0].equals("s")) {
                String newString = parameters[1];
                stringList.add(newString);
                return "String added: " + newString;
            } else {
                return "Invalid add request.";
            }
        } else if (url.getPath().equals("/search")) {
            String queryString = url.getQuery();
            String[] parameters = queryString.split("=");
            if (parameters.length == 2 && parameters[0].equals("s")) {
                String searchQuery = parameters[1];
                List<String> results = search(searchQuery);
                if (!results.isEmpty()) {
                    return "Search results: " + String.join(", ", results);
                } else {
                    return "No results found for: " + searchQuery;
                }
            } else {
                return "Invalid search request.";
            }
        } else if (url.getPath().equals("/")) {
            return "List of strings: " + String.join(", ", stringList);
        } else {
            return "404 Not Found!";
        }
    }

    // Function to search for strings containing a given substring
    private List<String> search(String substring) {
        List<String> results = new ArrayList<>();
        for (String s : stringList) {
            if (s.contains(substring)) {
                results.add(s);
            }
        }
        return results;
    }
}

public class SearchEngineServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
