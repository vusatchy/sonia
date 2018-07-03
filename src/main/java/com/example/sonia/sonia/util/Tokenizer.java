package com.example.sonia.sonia.util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

    private final static String SPLIT_SYMBOLS = "[,._!? ]";

    public static Set<String> tokenize(String word) {
        return Stream.of(word.split(SPLIT_SYMBOLS)).map(String::toLowerCase).collect(Collectors.toSet());
    }

}
