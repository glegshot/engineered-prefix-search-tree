package org.engineered.prefixsearch;

import java.util.List;

public class PrefixSearchTreeApplication {

    public static void main(String[] args) {

        PrefixSearchTree prefixSearchTree = new PrefixSearchTree();
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


        PrefixSearchTreeTraverser prefixSearchTreeTraverser = new PrefixSearchTreeTraverser();
        List<String> searchResults = prefixSearchTreeTraverser
                .search("g",prefixSearchTree.searchTreeRootNode);
        searchResults.forEach(System.out::println);



    }

}
