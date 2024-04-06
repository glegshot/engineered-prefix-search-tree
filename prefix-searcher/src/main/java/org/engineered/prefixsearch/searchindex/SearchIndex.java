package org.engineered.prefixsearch.searchindex;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchIndex {

    String FILE_URL = "https://api.github.com/repos/glegshot/engineered-prefix-search-tree/contents/search-index.json?ref=master";
    private JsonObject invertedIndex = new JsonObject();
    public void loadIndex() {
        this.invertedIndex = getIndexFile();
    }

    public void setInvertedIndex(JsonObject invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public List<String> getIndexKeys() {
        return new ArrayList<>(this.invertedIndex.asMap().keySet());
    }

    public List<JsonElement> getSearchSuggestionURLS(List<String> searchSuggestions) {
        return searchSuggestions.stream()
                .filter(searchSuggestion -> this.invertedIndex.has(searchSuggestion))
                .map(searchSuggestion -> this.invertedIndex.get(searchSuggestion))
                .collect(Collectors.toList());
    }

    private JsonObject getIndexFile() {
        JsonObject jsonObject = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(FILE_URL))
                    .headers("Accept", "application/vnd.github.raw+json")
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            jsonObject = gson.fromJson(response.body(), JsonObject.class);
            System.out.println(jsonObject.toString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }

}
