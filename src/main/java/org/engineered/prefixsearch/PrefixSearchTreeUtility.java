package org.engineered.prefixsearch;

public class PrefixSearchTreeUtility {

    public static String preProcessKey(String key) {
        key = key.toLowerCase();
        key = key.trim();
        return key;
    }

}
