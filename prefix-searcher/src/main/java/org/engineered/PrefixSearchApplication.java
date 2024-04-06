package org.engineered;

import com.google.gson.JsonElement;
import org.engineered.prefixsearch.searchindex.SearchIndex;
import org.engineered.prefixsearch.PrefixSearchTree;
import org.engineered.prefixsearch.PrefixSearchTreeTraverser;

import java.util.List;

public class PrefixSearchApplication {

    public static void main(String[] args) {
        SearchIndex searchIndex = new SearchIndex();
        searchIndex.loadIndex();

        PrefixSearchTree prefixSearchTree = new PrefixSearchTree();
        prefixSearchTree.insertKeys(searchIndex.getIndexKeys());

        String searchKey = "a";
        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> searchResults = prefixSearchTreeTraverser
                .search(searchKey,prefixSearchTree.getSearchTreeRootNode());

        List<JsonElement> results = searchIndex.getSearchSuggestionURLS(searchResults);
        results.forEach(System.out::println);


    }

}
