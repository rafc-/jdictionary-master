package org.ryan.dictionary.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class WordData {
    String word;
    Phonetic[] phonetics;
    Meaning[] meanings;

    @AllArgsConstructor
    @ToString
    @Getter
    public static class Phonetic {
        String text;
        String audio;
    }

    @AllArgsConstructor
    @ToString
    @Getter
    public static class Meaning {
        String partOfSpeech;
        Definition[] definitions;
    }

    @AllArgsConstructor
    @ToString
    @Getter
    public static class Definition {
        String definition;
        String[] synonyms;
        String example;
    }
}
