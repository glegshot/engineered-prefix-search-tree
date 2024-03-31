package org.engineered.prefixsearch;

import java.util.ArrayList;
import java.util.List;

import static org.engineered.prefixsearch.PrefixSearchTreeConstants.BASE_CHARACTER;
import static org.engineered.prefixsearch.PrefixSearchTreeConstants.MAX_SEARCH_SUGGESTIONS;
import static org.engineered.prefixsearch.PrefixSearchTreeConstants.MAX_TRAVERSAL_LEVEL;
import static org.engineered.prefixsearch.PrefixSearchTree.Node;

public class PrefixSearchTreeTraverser {

    private List<String> searchResults;

    public List<String> search(String key, Node searchTreeRootNode) {

        key = PrefixSearchTreeUtility.preProcessKey(key);

        Node prefixKeyEndNode = findForExactKeyMatch(key, searchTreeRootNode);

        this.searchResults = new ArrayList<>(MAX_SEARCH_SUGGESTIONS);

        if(prefixKeyEndNode != null) {
            if(prefixKeyEndNode.isEndOfWord) {
                this.searchResults.add(key);
            }
            findSuggestions(prefixKeyEndNode, key);
        }

        return searchResults;
    }

    private Node findForExactKeyMatch(String key, Node crawler) {
        for(int level = 0; level < key.length(); level++) {
            int index = key.charAt(level) - BASE_CHARACTER;
            if(crawler.elements[index] == null) {
                return null;
            }
            crawler = crawler.elements[index];
        }

        return crawler;
    }

    private void findSuggestions(Node crawler, String key) {
        if(crawler != null) {
            StringBuffer matchedPrefix = new StringBuffer(key);
            findNextMatch(crawler, matchedPrefix, 0);
        }
    }

    private void findNextMatch(Node crawler, StringBuffer currentMatchPrefix, int currentLevel) {
        if(maxTraversalLevelBreached(currentLevel) || maxSearchSuggestionsFound()){
            return;
        }

        int index = 0;
        for(Node childElement: crawler.getElements()) {
            if(childElement != null) {
                char key = (char) (index + (int) BASE_CHARACTER);
                currentMatchPrefix.append(key);
                if(childElement.isEndOfWord) {
                    searchResults.add(currentMatchPrefix.toString());
                }
                findNextMatch(childElement, currentMatchPrefix, currentLevel + 1);
                if(maxTraversalLevelBreached(currentLevel) || maxSearchSuggestionsFound()){
                    return;
                }
                currentMatchPrefix.deleteCharAt(currentMatchPrefix.length()-1);
            }
            index++;
        }
    }

    private boolean maxTraversalLevelBreached(int currentLevel) {
        return currentLevel > MAX_TRAVERSAL_LEVEL;
    }

    private boolean maxSearchSuggestionsFound() {
        return this.searchResults.size() >= MAX_SEARCH_SUGGESTIONS;
    }

}
