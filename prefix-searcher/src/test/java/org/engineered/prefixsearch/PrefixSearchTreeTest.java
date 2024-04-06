package org.engineered.prefixsearch;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PrefixSearchTreeTest {

    PrefixSearchTree prefixSearchTree;

    @BeforeEach
    public void createPrefixTree() {
        prefixSearchTree = new PrefixSearchTree();
        prefixSearchTree.insertKey("ant");
        prefixSearchTree.insertKey("ants");
        prefixSearchTree.insertKey("andaman");
        prefixSearchTree.insertKey("andy");
        prefixSearchTree.insertKey("anderson");
        prefixSearchTree.insertKey("annotations");
        prefixSearchTree.insertKey("anubis");
        prefixSearchTree.insertKey("network");
        prefixSearchTree.insertKey("index");
        prefixSearchTree.insertKey("invocation");
        prefixSearchTree.insertKey("interceptor");
        prefixSearchTree.insertKey("filter");
        prefixSearchTree.insertKey("filters");
        prefixSearchTree.insertKey("rest");
        prefixSearchTree.insertKey("restapi");
        prefixSearchTree.insertKey("giraffe");
        prefixSearchTree.insertKey("gandalf");
    }

    @Test
    public void testForSearchMatchSuggestions() {
        List<String> expectedSuggestions = List.of("andaman","andy","anderson");
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("and",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(expectedSuggestions.size(), actualSearchSuggestions.size());
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }

    @Test
    public void testForSearchEmptyMatchWhenNoMatch() {
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("no",prefixSearchTree.searchTreeRootNode);

        Assertions.assertTrue(actualSearchSuggestions.isEmpty());
    }

    @Test
    public void testForStopTraversalOnMaxLevelLimitBreach() {
        String veryLongWord = "invocationhandlinginterfaceimplemenationandabstraction";
        prefixSearchTree.insertKey(veryLongWord);

        List<String> expectedSuggestions = List.of("interceptor","invocation","index");

        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("in",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(expectedSuggestions.size(), actualSearchSuggestions.size());
        Assertions.assertFalse(actualSearchSuggestions.contains(veryLongWord));
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }

    @Test
    public void testForFetchMaxSuggestionsWhenMoreThanMaxSuggestionsPresent() {
        prefixSearchTree.insertKey("cat");
        prefixSearchTree.insertKey("cats");
        prefixSearchTree.insertKey("cars");
        prefixSearchTree.insertKey("carmen");
        prefixSearchTree.insertKey("caramel");
        prefixSearchTree.insertKey("cautious");

        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("ca",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(actualSearchSuggestions.size(), PrefixSearchTreeConstants.MAX_SEARCH_SUGGESTIONS);
    }

    @Test
    public void testForEmptyResultWhenSearchKeyContainsCharactersOutsideAlphabets() {
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("$12",prefixSearchTree.searchTreeRootNode);

        Assertions.assertTrue(actualSearchSuggestions.isEmpty());
    }

    @Test
    public void testForCaseInsensitiveSearch() {
        List<String> expectedSuggestions = List.of("filter","filters");
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("FI",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(expectedSuggestions.size(), actualSearchSuggestions.size());
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }

    @Test
    public void testForSpaceSeperatedMultipleKeys() {
        prefixSearchTree.insertKey("graph api");

        List<String> expectedSuggestions = List.of("giraffe","gandalf","graphapi");

        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("g",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(expectedSuggestions.size(), actualSearchSuggestions.size());
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }

    @Test
    public void testForRemovingCharactersOutsideAlphabetsAndReturningSuggestionsOnCompressedWords() {
        prefixSearchTree.insertKey("wireshark-version-1.0");
        prefixSearchTree.insertKey("wireless-fidelity");

        List<String> expectedSuggestions = List.of("wiresharkversion","wirelessfidelity");

        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("wi",prefixSearchTree.searchTreeRootNode);

        Assertions.assertEquals(expectedSuggestions.size(), actualSearchSuggestions.size());
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }


}
