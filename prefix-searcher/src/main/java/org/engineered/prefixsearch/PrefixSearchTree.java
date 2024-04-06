package org.engineered.prefixsearch;

import java.util.Arrays;
import java.util.List;

import static org.engineered.prefixsearch.PrefixSearchTreeConstants.BASE_CHARACTER;
import static org.engineered.prefixsearch.PrefixSearchTreeConstants.ELEMENT_ARRAY_SIZE;

public class PrefixSearchTree {
    Node searchTreeRootNode;

    public Node getSearchTreeRootNode() {
        return searchTreeRootNode;
    }

    public void setSearchTreeRootNode(Node searchTreeRootNode) {
        this.searchTreeRootNode = searchTreeRootNode;
    }

    public PrefixSearchTree() {
        this.searchTreeRootNode = new Node();
    }

    static class Node {

        public Node[] getElements() {
            return elements;
        }

        Node[] elements = new Node[ELEMENT_ARRAY_SIZE];
        boolean isEndOfWord;

        public Node() {
            isEndOfWord = false;
            Arrays.fill(elements, null);
        }
    }

    public void insertKeys(List<String> keys) {
        keys.forEach(this::insertKey);
    }

    public void insertKey(String key) {
        int index;
        key = PrefixSearchTreeUtility.preProcessKey(key);
        int keyLength = key.length();

        Node crawler = this.searchTreeRootNode;

        for(int level = 0; level < keyLength; level++) {
            index = key.charAt(level) - BASE_CHARACTER;
            if(crawler.elements[index] == null) {
                crawler.elements[index] = new Node();
            }
            crawler = crawler.elements[index];
        }

        crawler.isEndOfWord = true;
    }

}


