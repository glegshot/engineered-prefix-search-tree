package org.engineered.searchindex;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SearchIndexTest {

    SearchIndex searchIndex;


    @BeforeEach
    public void createSearchIndex() {
        String indexString = "{" +
                "\"array\":[{\"url\":\"https://glegshot.github.io/case/article?case=j1\",\"title\":\"Article1\"}," +
                "{\"url\":\"https://glegshot.github.io/case/article?case=j3\",\"title\":\"Article3\"}]," +
                "\"string\":[{\"url\":\"https://glegshot.github.io/case/article?case=j2\",\"title\":\"Article2\"}," +
                "{\"url\":\"https://glegshot.github.io/case/article?case=j3\",\"title\":\"Article3\"}]," +
                "\"overflow\":{\"url\":\"https://glegshot.github.io/case/article?case=j1\",\"title\":\"Article1\"}" +
                "}";
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(indexString, JsonObject.class);
        searchIndex = new SearchIndex();
        searchIndex.setInvertedIndex(jsonObject);
    }

    @Test
    public void testForReturnSearchSuggestionsWhenPresentInIndex() {
        List<JsonElement> actualSearchResults = searchIndex.getSearchSuggestionURLS(List.of("array"));
        Assertions.assertFalse(actualSearchResults.isEmpty());
    }

    @Test
    public void testForReturnEmptyResultsWhenNotFound() {
        List<JsonElement> actualSearchResults = searchIndex.getSearchSuggestionURLS(List.of("aindex"));
        Assertions.assertTrue(actualSearchResults.isEmpty());
    }

    @Test
    public void testForReturnKeysOfIndex() {
        List<String> expectedKeys = List.of("array","string","overflow");
        List<String> actualKeys = searchIndex.getIndexKeys();
        Assertions.assertEquals(expectedKeys.size(), actualKeys.size());
        Assertions.assertTrue(actualKeys.containsAll(expectedKeys));
    }

    @Test
    public void testForReturnKeysWhenIndexEmpty() {
        searchIndex = new SearchIndex();
        List<String> actualKeys = searchIndex.getIndexKeys();
        Assertions.assertTrue(actualKeys.isEmpty());
    }

}
