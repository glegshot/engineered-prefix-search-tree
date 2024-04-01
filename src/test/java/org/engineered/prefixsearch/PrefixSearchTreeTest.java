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
        prefixSearchTree.insertKey("network");
        prefixSearchTree.insertKey("index");
        prefixSearchTree.insertKey("invocation");
        prefixSearchTree.insertKey("annotations");
        prefixSearchTree.insertKey("anubis");
        prefixSearchTree.insertKey("interceptor");
        prefixSearchTree.insertKey("filter");
        prefixSearchTree.insertKey("filters");
        prefixSearchTree.insertKey("rest");
        prefixSearchTree.insertKey("restapi");
        prefixSearchTree.insertKey("graph-api");
        prefixSearchTree.insertKey("giraffe");
        prefixSearchTree.insertKey("gandalf");
    }

    @Test
    public void testForSearchMatchSuggestions() {
        List<String> expectedSuggestions = List.of("andaman","andy","anderson");
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("and",prefixSearchTree.searchTreeRootNode);
        Assertions.assertTrue(actualSearchSuggestions.containsAll(expectedSuggestions));
    }

    @Test
    public void testForSearchEmptyMatchWhenNoMatch() {
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> actualSearchSuggestions = prefixSearchTreeTraverser
                .search("no",prefixSearchTree.searchTreeRootNode);
        Assertions.assertTrue(actualSearchSuggestions.isEmpty());
    }

    public void testForStopTraversalOnMaxLevelLimitBreach() {

    }

    public void testForFetchMaxSuggestionsWhenMoreThanMaxSuggestionsPresent() {

    }

    public void testForEmptyResultWhenSearchKeyContainsCharactersOutsideAlphabets() {

    }

    public void testForCaseInsensitiveSearch() {

    }

    public void testForMultipleKeysWhenMatchPresentForEachKey() {

    }

}
