package org.engineered.prefixsearch;

public class PrefixSearchTreeUtility {

    public static String preProcessKey(String key) {
        key = key.toLowerCase();
        key = key.trim();
        key = key.replaceAll("[^a-zA-Z]+","");

        return key;
    }


}
